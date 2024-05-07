package com.example.mvvmarchitecture.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmarchitecture.databinding.ActivityHomeBinding
import com.example.mvvmarchitecture.ui.topheadline.TopHeadlineActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTopHeadlines.setOnClickListener {
            TopHeadlineActivity.startActivity(this)
        }
    }
}