package com.example.xmaster.domain.coin

import com.example.xmaster.data.CoinRepositoryImpl
import com.example.xmaster.domain.SuspendUseCase
import javax.inject.Inject

class LoadCoinsUseCase @Inject constructor(
    private val coinRepositoryImpl: CoinRepositoryImpl
) : SuspendUseCase<Unit, Unit>() {

    override suspend fun execute(parameters: Unit) {
        coinRepositoryImpl.loadCoins()
    }
}