package com.example.xmaster.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.xmaster.BuildConfig
import com.example.xmaster.data.model.Coin
import com.example.xmaster.data.model.CoinDeserializer
import com.example.xmaster.data.model.ImageDeserializer
import com.example.xmaster.data.model.ImagesResponse
import com.example.xmaster.data.network.ApiService
import com.example.xmaster.data.network.BASE_URL
import com.example.xmaster.data.network.ConnectivityDispatcher
import com.example.xmaster.data.network.KEY
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideConnectivityManager(ctx: Context): ConnectivityManager =
        ctx.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager


    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(createConverterFactory())
            .client(createClient()).build()

        return retrofit.create(ApiService::class.java)
    }

    private fun createClient() = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(logging)
        }
        addInterceptor(Interceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder()
                .header("X-CMC_PRO_API_KEY", KEY)
            val request = requestBuilder.build()
            it.proceed(request)
        })
    }.build()

    private fun createConverterFactory() = GsonConverterFactory.create(
        GsonBuilder()
            .registerTypeAdapter(ImagesResponse::class.java, ImageDeserializer())
            .registerTypeAdapter(Coin::class.java, CoinDeserializer())
            .create()
    )

}
