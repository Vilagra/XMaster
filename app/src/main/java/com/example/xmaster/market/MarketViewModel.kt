package com.example.xmaster.market

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
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
    internal val toastMessages: MediatorLiveData<Int>
    val mOnRefreshListener = SwipeRefreshLayout.OnRefreshListener { this.updateCoins() }

    init {
        mCoins = repository.getAllCoinsFromDb();
        toastMessages = Transformations.switchMap(mCoins) {newData -> convertToErrorResource(newData)} as MediatorLiveData<Int>
    }

    private fun updateCoins() {
        repository.loadCoins(mCoins)
    }

    fun convertToErrorResource(data: ResultWrapper<PagedList<Coin>>): SingleLiveEvent<Int>{
        val res = SingleLiveEvent<Int>()
        val status = data.status
        if (status == Status.ERROR){
          when(data.message){
              Constants.LOST_INTERNET_CONNECTION -> res.postValue(R.string.lost_internet)
              Constants.SERVER_PROBLEM -> res.postValue(R.string.server_problem)
          }
        }
        return res;
    }
}
