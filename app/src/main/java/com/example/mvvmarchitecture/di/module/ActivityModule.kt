package com.example.mvvmarchitecture.di.module

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmarchitecture.data.repository.TopHeadlineRepository
import com.example.mvvmarchitecture.di.ActivityContext
import dagger.Provides

class ActivityModule(private val activity: Activity) {
    @ActivityContext
    @Provides
    fun provideActivityContext():Context = activity


}