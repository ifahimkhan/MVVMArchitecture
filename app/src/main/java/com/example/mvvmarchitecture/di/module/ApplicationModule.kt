package com.example.mvvmarchitecture.di.module

import com.example.mvvmarchitecture.data.api.HeaderInterceptor
import com.example.mvvmarchitecture.data.api.NetworkService
import com.example.mvvmarchitecture.di.BaseUrl
import com.example.mvvmarchitecture.utils.AppConstant
import com.example.mvvmarchitecture.utils.logger.AppLogger
import com.example.mvvmarchitecture.utils.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule() {
    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = AppConstant.BASE_URL

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): NetworkService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(baseUrl)
            .build()
            .create(NetworkService::class.java)

    }

    @Singleton
    @Provides
    fun provideOkHttpClient(headerInterceptor: HeaderInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(headerInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideHeaderInterceptor() = HeaderInterceptor()

    @Provides
    @Singleton
    fun provideLogger(): Logger = AppLogger()

}