package com.example.xmaster.data.network

import android.net.ConnectivityManager
import javax.inject.Inject


class ConnectivityDispatcher @Inject constructor(val connectivityManager: ConnectivityManager) {

    fun hasConnection(): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}