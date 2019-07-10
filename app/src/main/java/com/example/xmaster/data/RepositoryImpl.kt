package com.example.xmaster.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bumptech.glide.Glide
import com.example.xmaster.data.database.AppDataBase
import com.example.xmaster.data.model.Coin
import com.example.xmaster.data.network.ConnectivityDispatcher
import com.example.xmaster.data.network.RetrofitHelper
import com.example.xmaster.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class RepositoryImpl constructor(
    val connectivityDispatcher: ConnectivityDispatcher,
    val appDataBase: AppDataBase,
    val context: Context
) : Repository {


    override fun getAllCoinsFromDb(): MediatorLiveData<ResultWrapper<PagedList<Coin>>> {
        val result = MediatorLiveData<ResultWrapper<PagedList<Coin>>>()
        val dataFromDataBase = LivePagedListBuilder(appDataBase.coinsDao().getAllCoins(), 10).build();
        result.addSource(dataFromDataBase) {
            result.postValue(ResultWrapper.success(it))
        }
        return result
    }

    suspend fun loadPictures() {
        val lictCoins = appDataBase.coinsDao().getAllCoinsList().toSet()
        val coinsWithoutPicture = lictCoins.filter { it.imageURL == null }
        for (lictCoin in coinsWithoutPicture.chunked(500)) {
            val coinsWithoutPictureIDsString = lictCoin.map { it.id }.joinToString(separator = ",")
            try {
                val result = RetrofitHelper.authService.getPicture(coinsWithoutPictureIDsString).suspendExecute()
                result.res?.forEach { images ->
                    coinsWithoutPicture.find { coin -> coin.id.toInt() === images.id.toInt() }?.imageURL = images.logo;
                    Glide.with(context)
                        .load(images.logo)
                        .preload(500, 500)
                }
            } catch (e: BadResponceException) {
            }
        }
        appDataBase.coinsDao().update(coinsWithoutPicture)
    }

    override fun loadCoins(): MutableLiveData<ResultWrapper<Unit>> {
        val livedata = MutableLiveData<ResultWrapper<Unit>>()
        livedata.postValue(ResultWrapper.loading(Unit))
        if (!connectivityDispatcher.hasConnection()) {
            livedata.postValue(ResultWrapper.error(Constants.LOST_INTERNET_CONNECTION, Unit))
        } else {
            MainScope().launch {
                Log.d("thread", "Default               : I'm working in thread ${Thread.currentThread().name}")
                loadCoinsFromNetwork(livedata)
            }
        }
        return livedata;
    }

    suspend fun loadCoinsFromNetwork(liveData: MutableLiveData<ResultWrapper<Unit>>) {
        withContext(Dispatchers.IO) {
            try {
                val result = RetrofitHelper.authService.getAll().suspendExecute()
                result.coins?.let {
                    appDataBase.coinsDao().insert(it)
                    liveData.postValue(ResultWrapper.success(Unit))
                    loadPictures()
                }
            } catch (e: BadResponceException) {
                ResultWrapper.error(
                    e.errorMessage ?: "coins==null",
                    liveData.value?.data
                )
            }
        }
    }
}

suspend fun <T> Call<T>.suspendExecute(): T {
    return suspendCoroutine { continuation ->
        val result = execute();
        if (result.isSuccessful) {
            result.body()?.let { continuation.resume(it) } ?: continuation.resumeWithException(
                BadResponceException(
                    result.errorBody()?.string()
                )
            )
        } else {
            continuation.resumeWithException(BadResponceException(null))
        }
    }
}


class BadResponceException(val errorMessage: String?) : Exception() {}