package com.example.mvvmarchitecture.di.module

import com.example.mvvmarchitecture.ui.countries.CountryAdapter
import com.example.mvvmarchitecture.ui.language.LanguageAdapter
import com.example.mvvmarchitecture.ui.newssource.NewsSourceAdapter
import com.example.mvvmarchitecture.ui.topheadline.TopHeadlineAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped


@Module
@InstallIn(ActivityComponent::class)
class ActivityModule() {

    @ActivityScoped
    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())



    @ActivityScoped
    @Provides
    fun provideNewsSourceAdapter() = NewsSourceAdapter(ArrayList())



    @ActivityScoped
    @Provides
    fun provideCountriesAdapter() = CountryAdapter(ArrayList())


    @ActivityScoped
    @Provides
    fun getLanguageAdapter() = LanguageAdapter(ArrayList())

}