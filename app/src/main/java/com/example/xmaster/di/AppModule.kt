package com.example.xmaster.di

import android.content.Context
import androidx.room.Room
import com.example.xmaster.MyApplication
import com.example.xmaster.data.database.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(application: MyApplication): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDataBase = Room.databaseBuilder(context, AppDataBase::class.java, "database-app").fallbackToDestructiveMigration().build()
}