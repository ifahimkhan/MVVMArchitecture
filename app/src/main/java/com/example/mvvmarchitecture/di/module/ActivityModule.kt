package com.example.mvvmarchitecture.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmarchitecture.data.repository.CountriesRepository
import com.example.mvvmarchitecture.data.repository.NewsSourcesRepository
import com.example.mvvmarchitecture.data.repository.TopHeadlineRepository
import com.example.mvvmarchitecture.di.ActivityContext
import com.example.mvvmarchitecture.ui.base.ViewModelProviderFactory
import com.example.mvvmarchitecture.ui.countries.CountryAdapter
import com.example.mvvmarchitecture.ui.countries.CountryViewModel
import com.example.mvvmarchitecture.ui.newssource.NewsSourceAdapter
import com.example.mvvmarchitecture.ui.newssource.NewsSourceViewModel
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

    @Provides
    fun provideNewsSourceViewModel(
        newsSourcesRepository: NewsSourcesRepository
    ): NewsSourceViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(NewsSourceViewModel::class) {
            NewsSourceViewModel(newsSourcesRepository)
        }).get(NewsSourceViewModel::class.java)
    }

    @Provides
    fun provideNewsSourceAdapter() = NewsSourceAdapter(ArrayList())

    @Provides
    fun provideCountryViewModel(
        countriesRepository: CountriesRepository
    ): CountryViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(CountryViewModel::class) {
            CountryViewModel(countriesRepository)
        }).get(CountryViewModel::class.java)
    }

    @Provides
    fun provideCountriesAdapter() = CountryAdapter(ArrayList())

}