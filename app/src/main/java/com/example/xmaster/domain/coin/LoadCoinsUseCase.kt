package com.example.xmaster.domain.coin

import com.example.xmaster.data.CoinRepositoryImpl
import com.example.xmaster.domain.SuspendUseCase

class LoadCoinsUseCase(
    private val coinRepositoryImpl: CoinRepositoryImpl
) : SuspendUseCase<Unit, Unit>() {

    override suspend fun execute(parameters: Unit) {
        coinRepositoryImpl.loadCoins()
    }
}