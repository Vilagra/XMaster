package com.example.xmaster.data.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.reflect.Type

class ImageResponse: Serializable{

    @SerializedName("logo")
    var image: String? = null
}

class ImageDeserializer : JsonDeserializer<ImageResponse> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ImageResponse {
        val result = ImageResponse()
        val id = json?.asJsonObject?.getAsJsonObject("data")?.keySet()?.iterator()?.next()
        result.image = json?.asJsonObject?.getAsJsonObject("data")?.getAsJsonObject(id)?.getAsJsonPrimitive("logo")?.asString
        return result
    }

}