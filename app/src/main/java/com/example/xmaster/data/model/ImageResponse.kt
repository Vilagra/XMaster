package com.example.xmaster.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ImageResponse: Serializable{

    @SerializedName("logo")
    var image: String? = null
}