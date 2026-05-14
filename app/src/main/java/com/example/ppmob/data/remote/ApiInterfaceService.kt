package com.example.ppmob.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.apply
import kotlin.jvm.java

object ApiInterfaceService {

    private const val SUPABASE_URL = "https://hnuqcoypylagjtahhjyx.supabase.co/"
    private const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhudXFjb3lweWxhZ2p0YWhoanl4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzUwNTMxNjAsImV4cCI6MjA5MDYyOTE2MH0.9VyyxVWketzKcSsUkWJ-qMY1FegKpDzZ4JLyP5POcmI"

    fun create(): ApiInterface {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                    .addHeader("apikey", API_KEY)
                    .addHeader("Authorization", "Bearer $API_KEY")

                val request = requestBuilder.build()
                chain.proceed(request)
            }

            .addInterceptor(HttpLoggingInterceptor().apply {
                HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(SUPABASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiInterface::class.java)
    }
}