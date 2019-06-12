package com.example.xmaster.data.network

import com.example.xmaster.data.model.CoinResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(GET_ALL)
    fun getAll(@Query("start") start: String = "1",
               @Query("limit") limit: String = "5000",
               @Query("convert") convert: String = "USD"): Call<CoinResponse>

}
