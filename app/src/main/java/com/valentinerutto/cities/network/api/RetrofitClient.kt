package com.valentinerutto.cities.network.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://connect-demo.mobile1.io/square1/"

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        addOkkhttpInterceptor(builder)

        return builder.build()
    }

    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    private fun addOkkhttpInterceptor(builder: OkHttpClient.Builder) {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            builder.addInterceptor(loggingInterceptor)
        }
    }

}
