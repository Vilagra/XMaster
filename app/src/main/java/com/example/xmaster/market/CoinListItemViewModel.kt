package com.example.xmaster.market

import com.example.xmaster.data.model.Coin
import java.math.RoundingMode
import java.text.DecimalFormat


class CoinListItemViewModel(item: Coin) {

    val name: String
    val symbol: String
    val cmc_rank: Int
    val price: Double
    val circulating_supply: Double
    val percent_change_24h: Double

    init {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        name = adaptName(item.name)
        symbol = item.symbol
        cmc_rank = item.cmc_rank
        price = df.format(item.price).toDouble()
        circulating_supply = item.circulating_supply
        percent_change_24h = df.format(item.percent_change_24h).toDouble()
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
