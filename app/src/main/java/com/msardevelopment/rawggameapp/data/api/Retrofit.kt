package com.msardevelopment.rawggameapp.data.api

import com.msardevelopment.rawggameapp.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {

    private val interceptor = HttpLoggingInterceptor()
    private val client: OkHttpClient

    init {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: RawgApi by lazy {
        retrofit.create(RawgApi::class.java)
    }

}