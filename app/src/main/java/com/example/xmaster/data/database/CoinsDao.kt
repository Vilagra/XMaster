package com.example.xmaster.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.xmaster.data.model.Coin


@Dao
abstract class CoinsDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProjects(coins: List<Coin>)

    @Query("select * from coin")
    abstract fun getAllProjects(): DataSource.Factory<Int, Coin>

}