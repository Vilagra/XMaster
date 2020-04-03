package com.example.xmaster.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.xmaster.utils.GeneralError
import com.example.xmaster.utils.Result
import com.example.xmaster.utils.Unexpected
import com.example.xmaster.utils.map
import java.lang.Exception


abstract class LiveDataUseCase<in Param, R> {

    operator fun invoke(parameters: Param): LiveData<Result<R>> {
        return liveData {
            try {
                val source : LiveData<Result<R>> = execute(parameters).map {
                    Result.Success(it)
                }
                emitSource(source)
            } catch (e: Exception){
                emit(Result.Error(e as? GeneralError ?: Unexpected()))
            }
        }
    }

    abstract fun execute(parameters: Param): LiveData<R>
}
