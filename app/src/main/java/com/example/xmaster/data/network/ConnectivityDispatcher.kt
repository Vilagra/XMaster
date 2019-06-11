package com.example.xmaster.data.network

import android.net.ConnectivityManager


class ConnectivityDispatcher private constructor(private val connectivityManager: ConnectivityManager) {

    companion object {

        @Volatile private var INSTANCE: ConnectivityDispatcher? = null

        fun getInstance(connectivityManager: ConnectivityManager): ConnectivityDispatcher =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: init(connectivityManager).also { INSTANCE = it }
                }

        private fun init(connectivityManager: ConnectivityManager) = ConnectivityDispatcher(connectivityManager)

    }

    fun hasConnection(): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}