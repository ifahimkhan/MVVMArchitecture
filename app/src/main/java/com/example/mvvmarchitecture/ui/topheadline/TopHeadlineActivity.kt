package com.example.mvvmarchitecture.ui.topheadline

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmarchitecture.MVVMApplication
import com.example.mvvmarchitecture.data.model.Article
import com.example.mvvmarchitecture.databinding.ActivityTopHeadlineBinding
import com.example.mvvmarchitecture.di.component.DaggerActivityComponent
import com.example.mvvmarchitecture.di.module.ActivityModule
import com.example.mvvmarchitecture.ui.base.BaseActivity
import com.example.mvvmarchitecture.ui.base.UiState
import com.example.mvvmarchitecture.utils.AppConstant
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineActivity : BaseActivity() {
    @Inject
    lateinit var topHeadlineViewModel: TopHeadlineViewModel

    @Inject
    lateinit var adapter: TopHeadlineAdapter
    private lateinit var binding: ActivityTopHeadlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        setupUI()
        setupObserver()
    }

    private fun initData() {
        val newsType: AppConstant.NewsType? = intent.getParcelableExtra(NEWS_BY)
        when (newsType) {
            is AppConstant.NewsType.COUNTRY -> {
                topHeadlineViewModel.fetchNewsByCountry(newsType.countryCode)
                Toast.makeText(this, "Selected code is ${newsType.countryCode}", Toast.LENGTH_SHORT)
                    .show()
            }

            is AppConstant.NewsType.SOURCE -> {
                topHeadlineViewModel.fetchNewsBySource(newsType.sourceId)
                Toast.makeText(this, "Selected code is ${newsType.sourceId}", Toast.LENGTH_SHORT)
                    .show()

            }

            is AppConstant.NewsType.LANGUAGE -> {
                topHeadlineViewModel.fetchNewsByLanguage(newsType.languageId)
                Toast.makeText(
                    this,
                    "Selected language is ${newsType.languageId}",
                    Toast.LENGTH_SHORT
                ).show()

            }

            null -> {
                topHeadlineViewModel.fetchTopHeadlines()
            }
        }

    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(State.CREATED) {
                topHeadlineViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@TopHeadlineActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }

                        UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun renderList(data: List<Article>) {
        adapter.addData(data)
        adapter.notifyDataSetChanged()
    }

    private fun setupUI() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as MVVMApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

    companion object {
        private const val NEWS_BY = "NEWS_BY"

        fun startActivity(context: Context, newsType: AppConstant.NewsType) {
            context.startActivity(Intent(context, TopHeadlineActivity::class.java).apply {
                this.putExtra(NEWS_BY, newsType)
            })
        }
    }
}