package com.example.xmaster.domain.coin

import androidx.paging.PagedList
import com.example.xmaster.data.CoinRepositoryImpl
import com.example.xmaster.data.model.Coin
import com.example.xmaster.domain.LiveDataUseCase
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val coinRepositoryImpl: CoinRepositoryImpl
) : LiveDataUseCase<Unit, PagedList<Coin>>() {

    override fun execute(parameters: Unit) = coinRepositoryImpl.getAllCoinsFromDb()
}