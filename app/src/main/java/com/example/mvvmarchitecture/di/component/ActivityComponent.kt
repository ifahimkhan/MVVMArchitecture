package com.example.mvvmarchitecture.di.component

import android.app.Activity
import com.example.mvvmarchitecture.di.ActivityScope
import com.example.mvvmarchitecture.di.module.ActivityModule
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class])
interface ActivityComponent {
}