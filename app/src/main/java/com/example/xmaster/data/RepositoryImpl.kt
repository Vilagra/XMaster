package com.example.xmaster.data

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bumptech.glide.Glide
import com.example.xmaster.data.database.AppDataBase
import com.example.xmaster.data.model.Coin
import com.example.xmaster.data.network.ConnectivityDispatcher
import com.example.xmaster.data.network.RetrofitHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RepositoryImpl(val connectivityDispatcher: ConnectivityDispatcher, val appDataBase: AppDataBase, val context: Context) : Repository {

    companion object {
        @Volatile
        private var INSTANCE: RepositoryImpl? = null

        fun getInstance(connectivityDispatcher: ConnectivityDispatcher, appDataBase: AppDataBase, context: Context): RepositoryImpl =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: init(connectivityDispatcher, appDataBase, context).also { INSTANCE = it }
            }

        private fun init(connectivityDispatcher: ConnectivityDispatcher, appDataBase: AppDataBase, context: Context) =
            RepositoryImpl(connectivityDispatcher, appDataBase, context)
    }

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

    override fun loadCoins(livedata: MediatorLiveData<ResultWrapper<PagedList<Coin>>>) {
        livedata.postValue(ResultWrapper.loading(livedata.value?.data))
        if (!connectivityDispatcher.hasConnection()) {
            livedata.postValue(ResultWrapper.error("Отсуствует интернет соединение!", livedata.value?.data))
            return
        }
        GlobalScope.launch {
            val result = RetrofitHelper.authService.getAll().execute()
            if (result.isSuccessful) {
                result.body()?.coins?.let {
                    appDataBase.coinsDao().insert(it)
                    loadPictures()
                } ?: livedata.postValue(
                    ResultWrapper.error(
                        result.errorBody()?.string() ?: "coins==null",
                        livedata.value?.data
                    )
                )
            } else {
                livedata.postValue(ResultWrapper.error(result.errorBody()?.string() ?: "", livedata.value?.data))
            }
        }

    }
}