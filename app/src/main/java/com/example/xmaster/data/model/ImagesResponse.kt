package com.example.xmaster.data.model


import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.io.Serializable
import java.lang.reflect.Type

data class Image(
    val id: Long,
    val logo: String
)

class ImagesResponse : Serializable {

    var images: List<Image> = listOf<Image>()
}

class ImageDeserializer : JsonDeserializer<ImagesResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ImagesResponse {
        val result = mutableListOf<Image>()
        val data = json?.asJsonObject?.getAsJsonObject("data")
        data?.keySet()?.forEach {
            context?.let { context ->
                result.add(
                    context.deserialize(
                        data.get(it),
                        Image::class.java
                    )
                )
            }
        }
        return ImagesResponse().apply { images = result }
    }
}
