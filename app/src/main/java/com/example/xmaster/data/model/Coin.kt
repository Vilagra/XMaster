package com.example.xmaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

@Entity
class Coin(
    @PrimaryKey
    val id: Long,
    val name: String,
    val symbol: String,
    val cmc_rank: Int,
    val price: Double,
    val circulating_supply: Double,
    val percent_change_24h: Double,
    val market_cap: Double,
    var imageURL: String? = null){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coin

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

class CoinDeserializer : JsonDeserializer<Coin> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Coin {
        val jsonObject = json?.asJsonObject
        val id = jsonObject?.getAsJsonPrimitive("id")?.asLong ?: 0
        val name = jsonObject?.getAsJsonPrimitive("name")?.asString ?: ""
        val symbol= jsonObject?.getAsJsonPrimitive("symbol")?.asString ?: ""
        val cmc_rank =jsonObject?.getAsJsonPrimitive("cmc_rank")?.asInt ?: -1
        val price = jsonObject?.getAsJsonObject("quote")?.getAsJsonObject("USD")?.getAsJsonPrimitive("price")?.asDouble ?: 0.0
        val circulating_supply =jsonObject?.get("circulating_supply")?.let { if(it.isJsonNull) 0.0 else it?.asDouble} ?: 0.0
        val percent_change_24h = jsonObject?.getAsJsonObject("quote")?.getAsJsonObject("USD")?.get("percent_change_24h")?.asDouble ?: 0.0
        val market_cap = jsonObject?.getAsJsonObject("quote")?.getAsJsonObject("USD")?.get("market_cap")?.let { if(it.isJsonNull) 0.0 else it?.asDouble} ?: 0.0
        return Coin(id, name, symbol, cmc_rank, price, circulating_supply, percent_change_24h, market_cap)
    }

}

