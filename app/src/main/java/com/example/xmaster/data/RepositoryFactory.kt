package com.example.xmaster.data

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.xmaster.data.database.AppDataBase
import com.example.xmaster.data.network.ConnectivityDispatcher

class RepositoryFactory {

    companion object {
        fun provideRepository(context: Context): Repository {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
            val connectivityStatus = ConnectivityDispatcher.getInstance(connectivityManager)
            val appDataBase: AppDataBase = Room.databaseBuilder(context, AppDataBase::class.java, "database-app").fallbackToDestructiveMigration().build()
            return RepositoryImpl.getInstance(connectivityStatus, appDataBase)
        }
    }
}