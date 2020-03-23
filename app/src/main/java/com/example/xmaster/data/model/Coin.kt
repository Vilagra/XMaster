package com.example.xmaster.data.model

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.xmaster.utils.NumberConverter
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

@Entity
data class Coin(
    @PrimaryKey
    val id: Long,
    val name: String,
    val symbol: String,
    val cmc_rank: Int,
    val price: Double,
    val circulating_supply: String,
    val percent_change_24h: Double,
    val market_cap: String,
    var imageURL: String? = null
)

class CoinDeserializer : JsonDeserializer<Coin> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Coin? {
         return json?.asJsonObject?.run {
            val id = getAsJsonPrimitive("id")?.asLong ?: 0
            val name = getAsJsonPrimitive("name")?.asString ?: ""
            val symbol = getAsJsonPrimitive("symbol")?.asString ?: ""
            val cmc_rank = getAsJsonPrimitive("cmc_rank")?.asInt ?: -1
            val circulating_supply =
                NumberConverter.convertDigitOnTouthandsComaSeparator(get("circulating_supply")?.let { if (it.isJsonNull) 0.0 else it?.asDouble }
                    ?: 0.0)
            getAsJsonObject("quote")?.getAsJsonObject("USD")?.let { usd ->
                val price = NumberConverter.doubleWithThreePointAfterComa(
                    usd.getAsJsonPrimitive("price")?.asDouble
                        ?: 0.0
                )

                val percent_change_24h = NumberConverter.doubleWithTwoPointAfterComa(
                    usd.get("percent_change_24h")?.asDouble
                        ?: 0.0
                )
                val market_cap = NumberConverter.convertDigitOnTouthandsComaSeparator(
                    usd.get("market_cap")?.let { if (it.isJsonNull) 0.0 else it?.asDouble }
                        ?: 0.0)
                Log.d("valer", "s")
                return Coin(
                    id,
                    name,
                    symbol,
                    cmc_rank,
                    price,
                    circulating_supply,
                    percent_change_24h,
                    market_cap
                )
            }
        } ?: null



    }

}

