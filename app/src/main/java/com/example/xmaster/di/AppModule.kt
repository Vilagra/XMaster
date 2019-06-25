package com.example.xmaster.di

import android.app.Application
import android.content.Context
import com.example.xmaster.data.RepositoryFactory
import com.example.xmaster.utils.CustomFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideViewModelFactory(): CustomFactory = CustomFactory(RepositoryFactory.provideRepository(provideContext()))
}