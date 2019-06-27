package com.example.xmaster.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.xmaster.data.RepositoryImpl
import com.example.xmaster.data.database.AppDataBase
import com.example.xmaster.data.network.ConnectivityDispatcher
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideConnectivityManager(ctx: Context): ConnectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager

    @Singleton
    @Provides
    fun provideConnectivityDispatcher(manager: ConnectivityManager) = ConnectivityDispatcher(manager)

    @Singleton
    @Provides
    fun provideRepository(connectivityDispatcher: ConnectivityDispatcher, appDataBase: AppDataBase, context: Context) =
        RepositoryImpl(connectivityDispatcher, appDataBase, context)
}
