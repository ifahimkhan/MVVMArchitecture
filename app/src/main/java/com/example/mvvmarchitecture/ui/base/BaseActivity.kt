package com.example.mvvmarchitecture.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmarchitecture.utils.Logger
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var logger: Logger
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}