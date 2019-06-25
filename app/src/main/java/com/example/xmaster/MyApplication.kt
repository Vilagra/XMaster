package com.example.xmaster

import android.app.Application
import com.example.xmaster.di.AppComponent
import com.example.xmaster.di.AppModule
import com.example.xmaster.di.DaggerAppComponent

class MyApplication : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = initDagger(this)
    }

    private fun initDagger(app: MyApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()

}