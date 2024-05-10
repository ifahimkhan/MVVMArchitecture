package com.example.mvvmarchitecture.ui.newssource

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmarchitecture.MVVMApplication
import com.example.mvvmarchitecture.data.model.Source
import com.example.mvvmarchitecture.databinding.ActivityNewsSourcesBinding
import com.example.mvvmarchitecture.di.component.DaggerActivityComponent
import com.example.mvvmarchitecture.di.module.ActivityModule
import com.example.mvvmarchitecture.ui.base.BaseActivity
import com.example.mvvmarchitecture.ui.base.UiState
import com.example.mvvmarchitecture.ui.topheadline.TopHeadlineActivity
import com.example.mvvmarchitecture.utils.AppConstant
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsSourcesActivity : BaseActivity() {
    private lateinit var binding: ActivityNewsSourcesBinding

    @Inject
    lateinit var viewModel: NewsSourceViewModel

    @Inject
    lateinit var mAdapter: NewsSourceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        binding = ActivityNewsSourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
        setUpObserver()

    }

    private fun injectDependency() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as MVVMApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@NewsSourcesActivity,
                                "${it.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is UiState.Loading -> {
                            binding.recyclerView.visibility = View.GONE
                            binding.progressBar.visibility = View.VISIBLE
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

    private fun renderList(data: List<Source>) {
        mAdapter.addData(data)
        mAdapter.notifyDataSetChanged()
    }

    private fun setUpUI() {
        var recyclerView = binding.recyclerView
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, linearLayoutManager.orientation))
        mAdapter.itemClickListener = {
            TopHeadlineActivity.startActivity(this, AppConstant.NewsType.SOURCE(it.id!!))
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, NewsSourcesActivity::class.java))
        }
    }
}