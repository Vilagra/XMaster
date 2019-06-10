package com.example.xmaster.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.xmaster.data.Coin
import com.example.xmaster.data.Repository
import com.example.xmaster.data.ResultWrapper

class MarketViewModel(val repository: Repository) : ViewModel() {


    private val mIsLoading = MutableLiveData<Boolean>()
    private val mIsErrorVisible = MutableLiveData<Boolean>()
    lateinit var mCoins: LiveData<ResultWrapper<PagedList<Coin>>>
    private val mOnRefreshListener = this::updateCoins

    init {
        mCoins = repository.getAllCoins();
    }

    private fun updateCoins() {
        repository.getAllCoins()
    }
}
