package com.example.xmaster.domain

import com.example.xmaster.utils.UseCaseResult
import com.example.xmaster.utils.Unexpected
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


abstract class ExecutableSuspendUseCase<in Param, R>(private val coroutineDispatcher: CoroutineDispatcher) : FlowUseCase<Param, R> {

    @ExperimentalCoroutinesApi
    override operator fun invoke(parameters: Param): Flow<UseCaseResult<R>> {
        return flow {
            emit(UseCaseResult.Loading)
            emit(execute(parameters))
        }.catch { exception ->
            exception.printStackTrace()
            emit(UseCaseResult.Error(Unexpected()))
        }.flowOn(coroutineDispatcher)
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: Param): UseCaseResult<R>
}
