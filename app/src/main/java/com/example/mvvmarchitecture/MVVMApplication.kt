package com.example.mvvmarchitecture

import android.app.Application
import com.example.mvvmarchitecture.di.component.ApplicationComponent

class MVVMApplication : Application(){
    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

    }

    private fun injectDependencies(){
       /* applicationComponent = DaggerApplicationComponent()
            .builder()
            .build()*/
    }
}