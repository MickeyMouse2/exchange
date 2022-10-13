package com.example.test.api

import com.example.test.data.Rates
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import retrofit2.http.Query


interface APIManager : Service {

}

class APIManagerImpl(private val api: Service) : APIManager {
    override fun rates(base: String, symbols: String): Single<Response<Rates>> {
        return api.rates()
            .subscribeOn(Schedulers.io())
    }
}