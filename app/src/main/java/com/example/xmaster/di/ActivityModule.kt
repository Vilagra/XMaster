package com.example.xmaster.di

import com.example.xmaster.ui.MainActivity
import com.example.xmaster.ui.assets.AssetsModule
import com.example.xmaster.ui.login.LoginActivity
import com.example.xmaster.ui.login.LoginModule
import com.example.xmaster.ui.market.MarketModule
import com.example.xmaster.ui.news.NewsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(
        modules = [
            NewsModule::class,
            AssetsModule::class,
            MarketModule::class
        ]
    )
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [
           LoginModule::class
        ]
    )
    abstract fun loginActivity(): LoginActivity
}