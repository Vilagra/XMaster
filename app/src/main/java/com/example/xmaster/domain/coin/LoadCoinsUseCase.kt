package com.example.xmaster.domain.coin

import com.example.xmaster.data.CoinRepositoryImpl
import com.example.xmaster.di.IoDispatcher
import com.example.xmaster.domain.ExecutableSuspendUseCase
import com.example.xmaster.utils.UseCaseResult
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoadCoinsUseCase @Inject constructor(
    private val coinRepositoryImpl: CoinRepositoryImpl,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : ExecutableSuspendUseCase<Unit, Unit>(dispatcher) {

    override suspend fun execute(parameters: Unit): UseCaseResult<Unit> {
        return coinRepositoryImpl.loadCoins()
    }
}