package com.example.xmaster.market

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.xmaster.data.Repository
import com.example.xmaster.data.ResultWrapper
import com.example.xmaster.data.model.Coin

class MarketViewModel(val repository: Repository) : ViewModel() {

    var mCoins: MediatorLiveData<ResultWrapper<PagedList<Coin>>>
    val mOnRefreshListener = SwipeRefreshLayout.OnRefreshListener { this.updateCoins() }

    init {
        mCoins = repository.getAllCoinsFromDb();
    }

    private fun updateCoins() {
        repository.loadCoins(mCoins)
    }
}
