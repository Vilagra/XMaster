package com.example.xmaster.domain

import com.example.xmaster.utils.UseCaseResult
import com.example.xmaster.utils.Unexpected
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


abstract class ObserwableFlowUseCase<in Param, R>(private val coroutineDispatcher: CoroutineDispatcher) : FlowUseCase<Param, R> {

    @ExperimentalCoroutinesApi
    override operator fun invoke(parameters: Param): Flow<UseCaseResult<R>> {
        return flow<UseCaseResult<R>> {
            emit(UseCaseResult.Loading)
            emitAll(execute((parameters)))
        }
            .catch { e -> emit(UseCaseResult.Error(Unexpected())) }
            .flowOn(coroutineDispatcher)

    }

    abstract fun execute(parameters: Param): Flow<UseCaseResult<R>>
}
