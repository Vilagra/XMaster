package com.example.xmaster.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class RepositoryImpl : Repository {

    override fun getAllCoins(): LiveData<ResultWrapper<List<Coin>>>? {
        val result = MediatorLiveData<ResultWrapper<PagedList<Coin>>>()
        val dataFromDataBase =
        return
    }
}