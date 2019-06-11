package com.example.xmaster.data

import androidx.lifecycle.MediatorLiveData
import androidx.paging.PagedList
import com.example.xmaster.data.model.Coin

interface Repository {

    fun getAllCoinsFromDb(): MediatorLiveData<ResultWrapper<PagedList<Coin>>>

    fun loadCoins(livedata: MediatorLiveData<ResultWrapper<PagedList<Coin>>>)
}
