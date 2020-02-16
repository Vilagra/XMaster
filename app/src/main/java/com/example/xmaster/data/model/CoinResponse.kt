package com.example.xmaster.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CoinResponse : Serializable {

    @SerializedName("data")
    var coins: List<Coin>? = null
}
