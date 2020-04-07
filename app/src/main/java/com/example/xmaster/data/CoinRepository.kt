package com.example.xmaster.data

import androidx.paging.PagedList
import com.example.xmaster.data.model.Coin
import com.example.xmaster.utils.UseCaseResult
import kotlinx.coroutines.flow.Flow


interface CoinRepository {

    fun getAllCoinsFromDb(): Flow<PagedList<Coin>>

    suspend fun loadCoins() : UseCaseResult<Unit>
}
