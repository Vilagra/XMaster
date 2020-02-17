package com.example.xmaster.data.network

import com.example.xmaster.data.model.CoinResponse
import com.example.xmaster.data.model.ImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(GET_ALL)
    suspend fun getAllCoins(@Query("start") start: String = "1",
                            @Query("limit") limit: String = "5000",
                            @Query("convert") convert: String = "USD"): Response<CoinResponse>

    @GET(GET_PICTURE)
    suspend fun getPicture(@Query("id") name: String): Response<ImagesResponse>

}
