package com.example.xmaster.ui.market

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmaster.data.CoinRepositoryImpl
import com.example.xmaster.domain.coin.GetCoinsUseCase
import com.example.xmaster.domain.coin.LoadCoinsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MarketViewModel @Inject constructor(
    app: Application,
    val getCoinsUseCase: GetCoinsUseCase,
    val loadCoinsUseCase: LoadCoinsUseCase
) : AndroidViewModel(app) {

    init {
        val coinsResult = getCoinsUseCase(Unit)
        viewModelScope.launch { loadCoinsUseCase(Unit) }
    }


}
