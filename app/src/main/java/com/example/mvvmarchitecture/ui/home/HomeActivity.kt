package com.example.mvvmarchitecture.ui.home

import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.mvvmarchitecture.ui.base.BaseActivity
import com.example.mvvmarchitecture.ui.base.NewsNavHost
import com.example.mvvmarchitecture.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                NewsNavHost()
            }
        }
    }
}