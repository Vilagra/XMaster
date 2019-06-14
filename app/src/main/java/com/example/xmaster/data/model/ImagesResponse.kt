package com.example.xmaster.data.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.io.Serializable
import java.lang.reflect.Type

class ImagesResponse: Serializable{

     val res: MutableList<Image> = ArrayList()
}

class ImageDeserializer : JsonDeserializer<ImagesResponse> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ImagesResponse {
        val result = ImagesResponse()
        val data = json?.asJsonObject?.getAsJsonObject("data")
        data?.keySet()?.forEach { result.res.add(context!!.deserialize(data.get(it), Image::class.java)) }
        return result
    }

}

class Image( val id: Long,
             val logo: String)