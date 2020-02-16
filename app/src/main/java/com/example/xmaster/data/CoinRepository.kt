package com.example.xmaster.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.xmaster.data.model.Coin


interface CoinRepository {

    fun getAllCoinsFromDb(): LiveData<PagedList<Coin>>

    suspend fun loadCoins()
}
