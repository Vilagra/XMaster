package com.example.xmaster.ui.market

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.xmaster.data.CoinRepositoryImpl
import javax.inject.Inject

class MarketViewModel @Inject constructor(
    app: Application,
    val coinRepositoryImpl: CoinRepositoryImpl
) : AndroidViewModel(app) {


}
