package com.example.xmaster.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Coin(
    @PrimaryKey(autoGenerate = true) var id: Long,
    val name: String,
    val symbol: String,
    val cmc_rank: Int,
    val price: Float,
    val circulating_supply: Long,
    val percent_change_24h: Float){

}
