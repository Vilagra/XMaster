package com.example.xmaster.domain

import com.example.xmaster.utils.GeneralError
import com.example.xmaster.utils.Result
import com.example.xmaster.utils.Unexpected


abstract class SuspendUseCase<in Param, R>() {

    suspend operator fun invoke(parameters: Param): Result<R> {
        return try {
            execute(parameters).let {
                Result.Success(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e as? GeneralError ?: Unexpected())
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: Param): R
}
