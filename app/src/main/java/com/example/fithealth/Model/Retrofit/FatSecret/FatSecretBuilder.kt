package com.example.fithealth.Model.Retrofit.FatSecret

import com.example.fithealth.Model.FoodApi.FatSecretApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FatSecretBuilder {
    private const val BASE_URL = "https://platform.fatsecret.com/rest/"

    private const val CONSUMER_KEY = "edfa8eda12794ebebb69758bdefdf9d2"
    private const val CONSUMER_SECRET = "23cdfbd83aaf4ae2b5bbe5cc12058599"

    private val interceptor =
        FatSecretInterceptor(consumerKey = CONSUMER_KEY, consumerSecret = CONSUMER_SECRET)


    //Create client with interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createService(): FatSecretApi = retrofit.create(FatSecretApi::class.java)
}