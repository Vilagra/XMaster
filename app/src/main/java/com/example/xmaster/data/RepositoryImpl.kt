package com.example.xmaster.data

import android.content.Context
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RepositoryImpl constructor(val connectivityDispatcher: ConnectivityDispatcher, val appDataBase: AppDataBase, val context: Context) : Repository {


    override fun getAllCoinsFromDb(): MediatorLiveData<ResultWrapper<PagedList<Coin>>> {
        val result = MediatorLiveData<ResultWrapper<PagedList<Coin>>>()
        val dataFromDataBase = LivePagedListBuilder(appDataBase.coinsDao().getAllCoins(), 10).build();
        result.addSource(dataFromDataBase) {
            result.postValue(ResultWrapper.success(it))
        }
        return result
    }

    fun loadPictures() {
        val lictCoins = appDataBase.coinsDao().getAllCoinsList().toSet()
        val coinsWithoutPicture = lictCoins.filter { it.imageURL == null }
        for (lictCoin in coinsWithoutPicture.chunked(500)) {
            val coinsWithoutPictureIDsString = lictCoin.map { it.id }.joinToString(separator = ",")
            val result = RetrofitHelper.authService.getPicture(coinsWithoutPictureIDsString).execute()
            if(result.isSuccessful){
                result.body()?.res?.forEach { images -> coinsWithoutPicture.find { coin -> coin.id.toInt() === images.id.toInt() }?.imageURL = images.logo;
                    Glide.with(context)
                        .load(images.logo)
                        .preload(500, 500)}
            }
        }
        appDataBase.coinsDao().update(coinsWithoutPicture)
    }

    override fun loadCoins(): MutableLiveData<ResultWrapper<Unit>> {
        val livedata = MutableLiveData<ResultWrapper<Unit>>()
        livedata.postValue(ResultWrapper.loading(Unit))
        if (!connectivityDispatcher.hasConnection()) {
            livedata.postValue(ResultWrapper.error(Constants.LOST_INTERNET_CONNECTION, Unit))
        } else{
            GlobalScope.launch {
                loadCoinsFromNetwork(livedata)
            }
        }
        return livedata;
    }

    suspend fun loadCoinsFromNetwork(liveData: MutableLiveData<ResultWrapper<Unit>>) {
        val result = RetrofitHelper.authService.getAll().execute()
        if (result.isSuccessful) {
            result.body()?.coins?.let {
                appDataBase.coinsDao().insert(it)
                liveData.postValue(ResultWrapper.success(Unit))
                loadPictures()
            } ?: liveData.postValue(
                ResultWrapper.error(
                    result.errorBody()?.string() ?: "coins==null",
                    liveData.value?.data
                )
            )
        } else {
            liveData.postValue(ResultWrapper.error(Constants.SERVER_PROBLEM ?: "", Unit))
        }
    }
}