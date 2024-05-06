package com.example.mvvmarchitecture.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmarchitecture.data.repository.TopHeadlineRepository
import com.example.mvvmarchitecture.di.ActivityContext
import com.example.mvvmarchitecture.ui.base.ViewModelProviderFactory
import com.example.mvvmarchitecture.ui.topheadline.TopHeadlineActivity
import com.example.mvvmarchitecture.ui.topheadline.TopHeadlineAdapter
import com.example.mvvmarchitecture.ui.topheadline.TopHeadlineViewModel
import dagger.Module
import dagger.Provides


@Module
class ActivityModule(private val activity: AppCompatActivity) {
    @ActivityContext
    @Provides
    fun provideActivityContext(): Context = activity

    @Provides
    fun provideTopHeadlineViewModel(
        topHeadlineRepository: TopHeadlineRepository
    ): TopHeadlineViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(TopHeadlineViewModel::class) {
                TopHeadlineViewModel(topHeadlineRepository)
            }).get(TopHeadlineViewModel::class.java)
    }


    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())


}