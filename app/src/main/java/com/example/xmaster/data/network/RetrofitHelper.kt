package com.example.xmaster.data.network

import com.example.xmaster.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private val retrofit = createRetrofit()

    val authService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    private fun createRetrofit(): Retrofit {

        val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createClient())

        return builder.build()
    }



    private fun createClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(logging)
        }

        httpClientBuilder.addInterceptor(Interceptor {
            val original = it.request()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .header("X-CMC_PRO_API_KEY", KEY) // <-- this is the important line

            val request = requestBuilder.build()
            it.proceed(request)
        })

        return httpClientBuilder.build()
    }

}