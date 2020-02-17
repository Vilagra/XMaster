package com.example.xmaster.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bumptech.glide.Glide
import com.example.xmaster.data.database.AppDataBase
import com.example.xmaster.data.model.Coin
import com.example.xmaster.data.network.ApiService
import com.example.xmaster.data.network.ConnectivityDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CoinRepositoryImpl @Inject constructor(
    private val connectivityDispatcher: ConnectivityDispatcher,
    private val appDataBase: AppDataBase,
    private val context: Context,
    private val apiService: ApiService
) : CoinRepository {


    override fun getAllCoinsFromDb(): LiveData<PagedList<Coin>> {
        return liveData {
            val dataFromDataBase =
                LivePagedListBuilder(appDataBase.coinsDao().getAllCoins(), 20).build()
            emitSource(dataFromDataBase)
        }
    }

    override suspend fun loadCoins() {
        if (!connectivityDispatcher.hasConnection()) {

        } else {
            loadCoinsFromNetwork()
        }
    }

    private suspend fun loadCoinsFromNetwork() {
        withContext(Dispatchers.IO) {
            val response = apiService.getAllCoins()
            if (response.isSuccessful) {
                response.body()?.coins?.let {
                    appDataBase.coinsDao().insert(it)
                    loadPictures()
                }
            }
        }
    }

    private suspend fun loadPictures() {
        val coinsWithoutPicture =
            appDataBase.coinsDao().getAllCoinsList().filter { it.imageURL == null }
        coinsWithoutPicture.chunked(500)
            .forEach { listCoins ->
                val coinsWithoutPictureIDsString =
                    listCoins.map { it.id }.joinToString(separator = ",")
                val response = apiService.getPicture(coinsWithoutPictureIDsString)
                if (response.isSuccessful) {
                    response.body()?.images?.forEach { images ->
                        coinsWithoutPicture.find { coin ->
                            coin.id == images.id
                        }?.imageURL = images.logo
                        Glide.with(context)
                            .load(images.logo)
                            .preload(64, 64)
                    }
                }
            }
        appDataBase.coinsDao().update(coinsWithoutPicture)
    }
}
