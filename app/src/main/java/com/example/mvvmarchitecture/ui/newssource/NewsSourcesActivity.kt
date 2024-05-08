package com.example.mvvmarchitecture.ui.newssource

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.databinding.ActivityNewsSourcesBinding
import com.example.mvvmarchitecture.ui.base.BaseActivity

class NewsSourcesActivity : BaseActivity() {
    private lateinit var binding: ActivityNewsSourcesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsSourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}