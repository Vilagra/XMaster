package com.example.xmaster.market

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.xmaster.R
import com.example.xmaster.data.Repository
import com.example.xmaster.data.ResultWrapper
import com.example.xmaster.data.Status
import com.example.xmaster.data.model.Coin
import com.example.xmaster.utils.Constants
import com.example.xmaster.utils.SingleLiveEvent

class MarketViewModel(val repository: Repository) : ViewModel() {

    val mCoins: MediatorLiveData<ResultWrapper<PagedList<Coin>>>
    internal val toastMessages: SingleLiveEvent<Int> = SingleLiveEvent()
    val mOnRefreshListener = SwipeRefreshLayout.OnRefreshListener { this.updateCoins() }

    init {
        mCoins = repository.getAllCoinsFromDb();
        mCoins.observeForever(Observer { val res = convertToErrorResource(it)
        toastMessages.postValue(res)})
    }

    private fun updateCoins() {
        repository.loadCoins(mCoins)
    }

    fun convertToErrorResource(data: ResultWrapper<PagedList<Coin>>): Int{
        val status = data.status
        if (status == Status.ERROR){
          when(data.message){
              Constants.LOST_INTERNET_CONNECTION -> return R.string.lost_internet
              Constants.SERVER_PROBLEM -> return R.string.server_problem
          }
        }
        return -1;
    }
}
