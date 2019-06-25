package com.example.xmaster.di

import com.example.xmaster.market.MarketFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(marketFragment: MarketFragment)
}