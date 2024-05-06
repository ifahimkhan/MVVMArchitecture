package com.example.mvvmarchitecture.di.component

import com.example.mvvmarchitecture.di.ActivityScope
import com.example.mvvmarchitecture.di.module.ActivityModule
import com.example.mvvmarchitecture.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(context: TopHeadlineActivity)
}