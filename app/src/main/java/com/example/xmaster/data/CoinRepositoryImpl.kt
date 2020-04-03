package com.example.xmaster.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bumptech.glide.Glide
import com.example.xmaster.data.database.AppDataBase
import com.example.xmaster.data.model.Coin
import com.example.xmaster.data.network.ApiService
import com.example.xmaster.data.network.ConnectivityDispatcher
import com.example.xmaster.utils.NetworkUnavailable
import com.example.xmaster.utils.UseCaseResult
import com.example.xmaster.utils.Unexpected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CoinRepositoryImpl @Inject constructor(
    private val connectivityDispatcher: ConnectivityDispatcher,
    private val appDataBase: AppDataBase,
    private val context: Context,
    private val apiService: ApiService
) : CoinRepository {


    @ExperimentalCoroutinesApi
    override fun getAllCoinsFromDb(): Flow<PagedList<Coin>> {
        return flow {
            val dataFromDataBase =
                LivePagedListBuilder(appDataBase.coinsDao().getAllCoins(), 20).build()
            emitAll(dataFromDataBase.asFlow())
        }
    }

    override suspend fun loadCoins() : UseCaseResult<Unit> {
        return if (connectivityDispatcher.hasConnection()) {
            loadCoinsFromNetwork()
        } else {
            UseCaseResult.Error(NetworkUnavailable())
        }
    }

    private suspend fun loadCoinsFromNetwork() : UseCaseResult<Unit> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getAllCoins()
            if (response.isSuccessful) {
                response.body()?.coins?.let {
                    appDataBase.coinsDao().insert(it)
                    loadPictures()
                    return@withContext UseCaseResult.Success(Unit)
                }
            }
            UseCaseResult.Error(Unexpected())
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
