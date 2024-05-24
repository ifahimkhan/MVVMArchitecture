package com.example.mvvmarchitecture.di.module

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.example.mvvmarchitecture.data.api.HeaderInterceptor
import com.example.mvvmarchitecture.data.api.NetworkService
import com.example.mvvmarchitecture.data.local.AppDatabase
import com.example.mvvmarchitecture.data.local.AppDatabaseService
import com.example.mvvmarchitecture.data.local.DatabaseService
import com.example.mvvmarchitecture.di.BaseUrl
import com.example.mvvmarchitecture.di.DatabaseName
import com.example.mvvmarchitecture.notifications.NotificationHelper
import com.example.mvvmarchitecture.utils.AppConstant
import com.example.mvvmarchitecture.utils.NetworkHelper
import com.example.mvvmarchitecture.utils.NetworkHelperImpl
import com.example.mvvmarchitecture.utils.logger.AppLogger
import com.example.mvvmarchitecture.utils.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
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

    @DatabaseName
    @Provides
    fun provideDatabaseName(): String = AppConstant.DATABASE_NAME

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseName databaseName: String
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            databaseName
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseService(appDatabase: AppDatabase): DatabaseService {
        return AppDatabaseService(appDatabase)
    }

    @Singleton
    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelperImpl(context = context)
    }

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideNotificationHelper(@ApplicationContext context: Context): NotificationHelper {

        return NotificationHelper(context)
    }
}