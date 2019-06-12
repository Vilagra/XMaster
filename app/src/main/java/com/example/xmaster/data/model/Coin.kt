package com.example.xmaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Coin(
    @PrimaryKey
    val name: String,
    val symbol: String,
    val cmc_rank: Int,
    val price: Float,
    val circulating_supply: Float,
    val percent_change_24h: Float){


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coin

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
