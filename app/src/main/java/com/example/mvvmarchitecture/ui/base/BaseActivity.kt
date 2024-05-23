package com.example.mvvmarchitecture.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmarchitecture.utils.logger.Logger
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var logger: Logger
}