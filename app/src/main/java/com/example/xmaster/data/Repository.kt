package com.example.xmaster.data

import androidx.lifecycle.LiveData

interface Repository {

    fun getAllCoins(): LiveData<List<Coin>>?
}
