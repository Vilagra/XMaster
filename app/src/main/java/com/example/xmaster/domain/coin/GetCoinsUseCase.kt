package com.example.xmaster.domain.coin

import androidx.paging.PagedList
import com.example.xmaster.data.CoinRepositoryImpl
import com.example.xmaster.data.model.Coin
import com.example.xmaster.di.IoDispatcher
import com.example.xmaster.domain.ObserwableFlowUseCase
import com.example.xmaster.utils.UseCaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val coinRepositoryImpl: CoinRepositoryImpl,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : ObserwableFlowUseCase<Unit, PagedList<Coin>>(dispatcher) {

    @ExperimentalCoroutinesApi
    override fun execute(parameters: Unit) =
        coinRepositoryImpl.getAllCoinsFromDb().map {
            UseCaseResult.Success(it)
        }

}