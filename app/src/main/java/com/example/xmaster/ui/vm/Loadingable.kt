package com.example.xmaster.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

interface Loadingable {

    val loading: LiveData<Boolean>
}

class LoadingableImpl @Inject constructor() : Loadingable{

    val _loading = MutableLiveData<Boolean>()
    override val loading: LiveData<Boolean> = _loading

    fun setLoading(isLoading: Boolean){
        _loading.value = isLoading
    }
}