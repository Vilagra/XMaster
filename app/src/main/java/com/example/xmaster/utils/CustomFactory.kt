package com.example.xmaster.utils

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.xmaster.data.Repository
import com.example.xmaster.market.MarketViewModel

class CustomFactory(
    private val mRepo: Repository
) : ViewModelProvider.NewInstanceFactory() {

    @NonNull
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        return MarketViewModel(mRepo) as T
    }
}