package com.example.xmaster.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.xmaster.data.RepositoryImpl
import com.example.xmaster.data.database.AppDataBase
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
    fun provideViewModelFactory(repo: RepositoryImpl): CustomFactory = CustomFactory(repo)

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDataBase = Room.databaseBuilder(context, AppDataBase::class.java, "database-app").fallbackToDestructiveMigration().build()
}