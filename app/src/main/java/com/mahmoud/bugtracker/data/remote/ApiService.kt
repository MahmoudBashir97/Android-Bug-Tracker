package com.mahmoud.bugtracker.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL_GOOGLE_SHEET = "https://script.google.com/"
    private const val BASE_URL_IMAGE_URL = "https://api.imgur.com"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private fun buildRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun createBug(): GoogleSheetsApi {
        return buildRetrofit(BASE_URL_GOOGLE_SHEET).create(GoogleSheetsApi::class.java)
    }

    fun createImageUrl(): ImgurApi {
        return buildRetrofit(BASE_URL_IMAGE_URL).create(ImgurApi::class.java)
    }
}