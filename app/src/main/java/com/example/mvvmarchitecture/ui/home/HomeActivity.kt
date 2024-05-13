package com.example.mvvmarchitecture.ui.home

import android.os.Bundle
import com.example.mvvmarchitecture.databinding.ActivityHomeBinding
import com.example.mvvmarchitecture.ui.base.BaseActivity
import com.example.mvvmarchitecture.ui.countries.CountryActivity
import com.example.mvvmarchitecture.ui.language.LanguageActivity
import com.example.mvvmarchitecture.ui.newssource.NewsSourcesActivity
import com.example.mvvmarchitecture.ui.search.SearchActivity
import com.example.mvvmarchitecture.ui.topheadline.TopHeadlineActivity
import com.example.mvvmarchitecture.utils.AppConstant

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTopHeadlines.setOnClickListener {
            TopHeadlineActivity.startActivity(this, AppConstant.NewsType.COUNTRY("IN"))
        }
        binding.btnNewsSources.setOnClickListener {
            NewsSourcesActivity.startActivity(this)
        }
        binding.btnCountries.setOnClickListener {
            CountryActivity.startActivity(this)
        }
        binding.btnLanguages.setOnClickListener {
            LanguageActivity.startActivity(this)
        }
        binding.btnSearch.setOnClickListener {
            SearchActivity.startActivity(this)
        }
    }
}