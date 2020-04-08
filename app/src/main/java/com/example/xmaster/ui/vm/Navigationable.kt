package com.example.xmaster.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.xmaster.utils.Event
import javax.inject.Inject

interface Navigationable<DESTINATION: Destination> {

    val destination: LiveData<Event<DESTINATION>>
}

class NavigationableImpl<DESTINATION: Destination> @Inject constructor() : Navigationable<DESTINATION>{

    private val _destination = MutableLiveData<Event<DESTINATION>>()
    override val destination: LiveData<Event<DESTINATION>> = _destination

    fun setError(destination: DESTINATION){
        _destination.value = Event(destination)
    }
}