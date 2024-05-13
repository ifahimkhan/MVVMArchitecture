package com.example.mvvmarchitecture.di.component

import com.example.mvvmarchitecture.di.ActivityScope
import com.example.mvvmarchitecture.di.module.ActivityModule
import com.example.mvvmarchitecture.ui.countries.CountryActivity
import com.example.mvvmarchitecture.ui.language.LanguageActivity
import com.example.mvvmarchitecture.ui.newssource.NewsSourcesActivity
import com.example.mvvmarchitecture.ui.search.SearchActivity
import com.example.mvvmarchitecture.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(context: TopHeadlineActivity)
    fun inject(context: NewsSourcesActivity)
    fun inject(context: CountryActivity)
    fun inject(context: LanguageActivity)
    fun inject(context: SearchActivity)
}