package com.example.test.api

import com.example.test.data.Rates
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface Service {

    @GET("fixer/latest")
    fun rates(
        @Query("base") base: String = "EUR",
        @Query("symbols") symbols: String = "USD"
    ): Single<Response<Rates>>
}
