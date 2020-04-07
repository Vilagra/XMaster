package com.example.xmaster.domain

import com.example.xmaster.utils.UseCaseResult
import kotlinx.coroutines.flow.Flow

interface FlowUseCase<in Param, R> {
    operator fun invoke(parameters: Param): Flow<UseCaseResult<R>>
}