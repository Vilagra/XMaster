package com.example.xmaster.data

import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.xmaster.data.database.AppDataBase
import com.example.xmaster.data.model.Coin
import com.example.xmaster.data.network.ConnectivityDispatcher
import com.example.xmaster.data.network.RetrofitHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RepositoryImpl(val connectivityDispatcher: ConnectivityDispatcher, val appDataBase: AppDataBase) : Repository {

    companion object {
        @Volatile
        private var INSTANCE: RepositoryImpl? = null

        fun getInstance(connectivityDispatcher: ConnectivityDispatcher, appDataBase: AppDataBase): RepositoryImpl =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: init(connectivityDispatcher, appDataBase).also { INSTANCE = it }
            }

        private fun init(connectivityDispatcher: ConnectivityDispatcher, appDataBase: AppDataBase) =
            RepositoryImpl(connectivityDispatcher, appDataBase)
    }

    override fun getAllCoinsFromDb(): MediatorLiveData<ResultWrapper<PagedList<Coin>>> {
        val result = MediatorLiveData<ResultWrapper<PagedList<Coin>>>()
        val dataFromDataBase = LivePagedListBuilder(appDataBase.coinsDao().getAllCoins(), 10).build();
        result.addSource(dataFromDataBase) {
            result.postValue(ResultWrapper.success(it))
            loadPictures(it)
        }
        return result
    }

    fun loadPictures(it: PagedList<Coin>) {
        GlobalScope.launch {
            val resList = ArrayList<Coin>()
            for (coin in it) {
                coin.imageURL ?: run {
                    val result = RetrofitHelper.authService.getPicture(coin.name).execute()
                    if(result.isSuccessful){
                        result.body()?.image?.let {
                            coin.imageURL = it
                            resList.add(coin) }
                    }
                }
            }
            appDataBase.coinsDao().update(resList)
        }
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