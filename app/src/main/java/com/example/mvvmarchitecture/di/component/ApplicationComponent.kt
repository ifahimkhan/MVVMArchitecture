package com.example.mvvmarchitecture.di.component

import android.content.Context
import com.example.mvvmarchitecture.MVVMApplication
import com.example.mvvmarchitecture.data.api.NetworkService
import com.example.mvvmarchitecture.data.repository.CountriesRepository
import com.example.mvvmarchitecture.data.repository.NewsRepository
import com.example.mvvmarchitecture.data.repository.NewsSourcesRepository
import com.example.mvvmarchitecture.data.repository.TopHeadlineRepository
import com.example.mvvmarchitecture.di.ApplicationContext
import com.example.mvvmarchitecture.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: MVVMApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getTopHeadlineRepository(): TopHeadlineRepository
    fun getNewsSourceRepository(): NewsSourcesRepository

    fun getCountriesRepository(): CountriesRepository
    fun getNewsReporsitory(): NewsRepository
}