package com.example.xmaster.ui.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.xmaster.data.model.Coin
import com.example.xmaster.domain.coin.GetCoinsUseCase
import com.example.xmaster.domain.coin.LoadCoinsUseCase
import com.example.xmaster.ui.BaseViewModel
import com.example.xmaster.utils.ErrorHandler
import com.example.xmaster.utils.Event
import com.example.xmaster.utils.UseCaseResult
import com.example.xmaster.utils.handleResult
import com.example.xmaster.utils.mapToStringResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MarketViewModel @Inject constructor(
    getCoinsUseCase: GetCoinsUseCase,
    private val loadCoinsUseCase: LoadCoinsUseCase
) : BaseViewModel(), SwipeRefreshLayout.OnRefreshListener {

    private val _coins = MediatorLiveData<PagedList<Coin>>()
    val coins: LiveData<PagedList<Coin>> = _coins

    private val _isDataAvailable = MutableLiveData<Boolean>()
    val isDataAvailable: LiveData<Boolean> = _isDataAvailable

    init {
        val coinsResult = getCoinsUseCase(Unit)
        viewModelScope.launch {
            coinsResult.collect { result ->
                result.handleResult(handleSuccess = { list ->
                    _coins.value = list
                })
                _isDataAvailable.value =
                    (result is UseCaseResult.Error && _coins.value.isNullOrEmpty())
                            || (result is UseCaseResult.Success && result.data.isNullOrEmpty())
            }

        }
        viewModelScope.launch {
            loadCoinsUseCase(Unit).collect {
                it.handleResult(handleError = {
                    _errorMessage.value = Event(it.mapToStringResource())
                })
            }
        }
    }

    override fun onRefresh() {
        viewModelScope.launch {
            loadCoinsUseCase(Unit).collect {
                it.handleResult(
                    handleError = {
                    _errorMessage.value = Event(it.mapToStringResource())
                },
                    handleLoading = { _loading.value = it }
                )
            }
        }
    }

}
