package com.example.xmaster.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.xmaster.utils.Event
import javax.inject.Inject

interface Errorable {

    val errorMessage: LiveData<Event<Int>>
}

class ErrorableImpl @Inject constructor() : Errorable{

    private val _errorMessage = MutableLiveData<Event<Int>>()
    override val errorMessage: LiveData<Event<Int>> = _errorMessage

    fun setError(res: Int){
        _errorMessage.value = Event(res)
    }
}