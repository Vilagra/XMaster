package com.example.xmaster.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.xmaster.BuildConfig
import com.example.xmaster.data.model.Coin

@Database(entities = [Coin::class], version = BuildConfig.VERSION_CODE, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun coinsDao(): CoinsDao
}