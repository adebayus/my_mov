package com.sebade.relasiroom.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {
        private val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        private val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://bwa-movies-13cba-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val retrofitService: ApiService by lazy {
            retrofitBuilder.create(ApiService::class.java)
        }
    }
}