package com.example.xmaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

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

