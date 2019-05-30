package com.example.xmaster.data

import androidx.lifecycle.LiveData

class RepositoryImpl : Repository {

    override fun getAllCoins(): LiveData<List<Coin>>? {
       //RetrofitHelper.authService.
        return null;
    }
}