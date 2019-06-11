package com.example.xmaster.market

import com.example.xmaster.data.model.Coin


class CoinListItemViewModel(item: Coin) {

    val name: String
    val symbol: String
    val cmc_rank: Int
    val price: Float
    val circulating_supply: Long
    val percent_change_24h: Float

    init {
       name = item.name
        symbol = item.symbol
        cmc_rank = item.cmc_rank
        price = item.price
        circulating_supply = item.circulating_supply
        percent_change_24h = item.percent_change_24h
    }
}
