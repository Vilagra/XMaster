package com.example.xmaster.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xmaster.utils.Event

open class BaseViewModel : ViewModel(){

    protected val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading


    protected val _errorMessage = MutableLiveData<Event<Int>>()
    val errorMessage: LiveData<Event<Int>> = _errorMessage
}