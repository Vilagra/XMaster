package com.google.samples.apps.iosched.shared.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.example.xmaster.utils.Result
import java.lang.Exception


abstract class LiveDataUseCase<in Param, R>() {
    
    operator fun invoke(parameters: Param): LiveData<Result<R>> {
        return liveData {
            try {
                val result = execute(parameters)
                emitSource(Transformations.map(result){
                    Result.Success(it)
                })
            } catch (e: Exception){
                emit(Result.Error(e))
            }
        }
    }

    abstract fun execute(parameters: Param): LiveData<R>
}
