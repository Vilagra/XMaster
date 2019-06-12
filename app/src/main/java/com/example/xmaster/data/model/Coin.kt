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
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
