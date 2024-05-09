package com.example.mvvmarchitecture.di.module

import android.app.Application
import android.content.Context
import com.example.mvvmarchitecture.data.api.NetworkService
import com.example.mvvmarchitecture.di.ApplicationContext
import com.example.mvvmarchitecture.di.BaseUrl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {
    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application.applicationContext
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(baseUrl)
            .build()
            .create(NetworkService::class.java)

    }
}