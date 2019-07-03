package com.example.xmaster.market

import androidx.lifecycle.*
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

    private val _mCoins: MediatorLiveData<ResultWrapper<PagedList<Coin>>>
    val mCoins: LiveData<ResultWrapper<PagedList<Coin>>>
    get() = _mCoins
    private val _toastMessages: SingleLiveEvent<Int> = SingleLiveEvent()
    val toastMessages: SingleLiveEvent<Int>
        get() = _toastMessages
    val mOnRefreshListener = SwipeRefreshLayout.OnRefreshListener { this.updateCoins() }
    val uploadCoins: MutableLiveData<Unit> = MutableLiveData()
    private val _loadCoinsRequest: LiveData<ResultWrapper<Unit>> = Transformations.switchMap(uploadCoins){res-> repository.loadCoins()}
    val mLoad: LiveData<ResultWrapper<Unit>>
        get() = _loadCoinsRequest




    init {
        _mCoins = repository.getAllCoinsFromDb();
        _toastMessages.addSource(_loadCoinsRequest){ wrapper ->
            _toastMessages.postValue(convertToErrorResource(wrapper))}
    }

    private fun updateCoins() {
        uploadCoins.value = Unit
    }

    fun convertToErrorResource(data: ResultWrapper<Any>): Int{
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
