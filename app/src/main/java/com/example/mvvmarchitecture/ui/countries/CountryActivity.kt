package com.example.mvvmarchitecture.ui.countries

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmarchitecture.MVVMApplication
import com.example.mvvmarchitecture.data.model.Country
import com.example.mvvmarchitecture.databinding.ActivityCountryBinding
import com.example.mvvmarchitecture.di.component.DaggerActivityComponent
import com.example.mvvmarchitecture.di.module.ActivityModule
import com.example.mvvmarchitecture.ui.base.BaseActivity
import com.example.mvvmarchitecture.ui.base.UiState
import com.example.mvvmarchitecture.ui.topheadline.TopHeadlineActivity
import com.example.mvvmarchitecture.utils.AppConstant
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryActivity : BaseActivity() {
    private lateinit var binding: ActivityCountryBinding

    @Inject
    lateinit var viewModel: CountryViewModel

    @Inject
    lateinit var mAdapter: CountryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependency()
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
        setUpObserver()
    }

    private fun injectDependency() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as MVVMApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build().inject(this)
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@CountryActivity, it.message, Toast.LENGTH_SHORT)
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

    private fun renderList(data: List<Country>) {
        mAdapter.addData(data)
        mAdapter.notifyDataSetChanged()
        Log.e("TAG", "renderList: ${binding.recyclerView.visibility}")
        Log.d("TAG", "renderList: " + data.size)
    }

    private fun setUpUI() {
        val recyclerview = binding.recyclerView
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.addItemDecoration(DividerItemDecoration(this, linearLayoutManager.orientation))
        recyclerview.adapter = mAdapter
        mAdapter.itemClickListener = {
            TopHeadlineActivity.startActivity(this, AppConstant.NewsType.COUNTRY(it.code))
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, CountryActivity::class.java))
        }
    }
}