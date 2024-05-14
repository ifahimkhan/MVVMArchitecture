package com.example.mvvmarchitecture.ui.language

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmarchitecture.MVVMApplication
import com.example.mvvmarchitecture.data.model.Language
import com.example.mvvmarchitecture.databinding.ActivityLanguageBinding
import com.example.mvvmarchitecture.di.component.DaggerActivityComponent
import com.example.mvvmarchitecture.di.module.ActivityModule
import com.example.mvvmarchitecture.ui.base.UiState
import com.example.mvvmarchitecture.ui.topheadline.TopHeadlineActivity
import com.example.mvvmarchitecture.utils.AppConstant
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLanguageBinding

    @Inject
    lateinit var mAdapter: LanguageAdapter

    @Inject
    lateinit var viewModel: LanguageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependency()
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUI()
        setUpObserver()

    }

    private fun setUpObserver() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect {
                    Log.e(LanguageActivity::class.java.name, "observer called")

                    when (it) {
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@LanguageActivity,
                                "${it.message}",
                                Toast.LENGTH_SHORT
                            ).show()
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

    private fun renderList(data: List<Language>) {
        mAdapter.addData(data)
        mAdapter.notifyDataSetChanged()
    }


    private fun setUpUI() {
        binding.recyclerView.apply {
            val layoutManager = LinearLayoutManager(this@LanguageActivity)
            this.layoutManager = layoutManager
            adapter = mAdapter
            this.addItemDecoration(
                DividerItemDecoration(
                    this@LanguageActivity,
                    layoutManager.orientation
                )
            )
            mAdapter.itemClickListener = {
                TopHeadlineActivity.startActivity(
                    this@LanguageActivity,
                    AppConstant.NewsType.LANGUAGE(it.code)
                )
            }
        }
        binding.btnFetchNewsByLanguages.setOnClickListener {
            if (mAdapter.getSelectedLanguages().size > 2) {
                Toast.makeText(this, "Max 2 languages allowed ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val selectedLanguages = mAdapter.getSelectedLanguages().joinToString { it.code }
            TopHeadlineActivity.startActivity(
                this,
                AppConstant.NewsType.LANGUAGE(selectedLanguages)
            )
        }

    }

    private fun injectDependency() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as MVVMApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build().inject(this)
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, LanguageActivity::class.java))
        }
    }
}