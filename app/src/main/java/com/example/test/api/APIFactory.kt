package com.example.test.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIFactory {

    companion object {
        fun getBaseUrl(): String = "https://api.apilayer.com"

        fun getInstance(): Service {
            val client = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor {
                    val request = it.request().newBuilder()

                    request.addHeader("apikey", "KC6sf7UVmxgGuHQjz76ck2EummMdJ1vP")

                    it.proceed(request.build())
                }
                .build()

            return Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build()
                .create(Service::class.java)
        }
    }
}