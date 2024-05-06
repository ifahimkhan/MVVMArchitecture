package com.example.mvvmarchitecture

import android.app.Application
import com.example.mvvmarchitecture.di.component.ApplicationComponent
import com.example.mvvmarchitecture.di.component.DaggerApplicationComponent
import com.example.mvvmarchitecture.di.module.ApplicationModule

class MVVMApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()

    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(
            ApplicationModule(this)
        ).build()
        applicationComponent.inject(this)
    }
}