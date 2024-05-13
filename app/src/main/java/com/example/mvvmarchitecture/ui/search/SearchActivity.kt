package com.example.mvvmarchitecture.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmarchitecture.MVVMApplication
import com.example.mvvmarchitecture.data.model.Article
import com.example.mvvmarchitecture.databinding.ActivitySearchBinding
import com.example.mvvmarchitecture.di.component.DaggerActivityComponent
import com.example.mvvmarchitecture.di.module.ActivityModule
import com.example.mvvmarchitecture.ui.base.UiState
import com.example.mvvmarchitecture.ui.topheadline.TopHeadlineAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    @Inject
    lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var mAdapter: TopHeadlineAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependency()
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUI()
        setUpObserver()
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@SearchActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }

                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                    }

                }
            }
        }

    }

    private fun renderList(data: List<Article>) {
        mAdapter.clearData()
        mAdapter.addData(data)
        mAdapter.notifyDataSetChanged()
    }

    private fun setUpUI() {
        binding.recyclerView.apply {
            val layoutManager = LinearLayoutManager(this@SearchActivity)
            this.layoutManager = layoutManager
            this.addItemDecoration(
                DividerItemDecoration(
                    this@SearchActivity,
                    layoutManager.orientation
                )
            )
            this.adapter = mAdapter
        }

        binding.searchViewNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.fetchNewsBySearch(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun injectDependency() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as MVVMApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build().inject(this)
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, SearchActivity::class.java))
        }
    }
}