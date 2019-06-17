package com.example.xmaster.market

import com.example.xmaster.data.model.Coin
import com.example.xmaster.utils.NumberConverter


class CoinListItemViewModel(item: Coin) {

    val name: String
    val symbol: String
    val cmc_rank: Int
    val price: String
    val circulating_supply: String
    val percent_change_24h: Double
    val market_cap: String


    init {
        name = adaptName(item.name)
        symbol = item.symbol
        cmc_rank = item.cmc_rank
        price = NumberConverter.doubleWithThreePointAfterComa(item.price).toString()
        circulating_supply = NumberConverter.convertDigitOnTouthandsComaSeparator(item.circulating_supply)
        percent_change_24h = NumberConverter.doubleWithTwoPointAfterComa(item.percent_change_24h)
        market_cap = NumberConverter.convertDigitOnTouthandsComaSeparator(NumberConverter.doubleWithTwoPointAfterComa(item.market_cap))
    }

    fun adaptName(name: String): String {
        when (name) {
            "Bitcoin" -> return "Батя"
            "Ethereum" -> return "Izotium"
            "Bitcoin Cash" -> return "Olegka cash"
            "EOS" -> return "proEbOS"
            else -> return name
        }
    }
}
