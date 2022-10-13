package com.example.test.usecase

import com.example.test.api.Service
import com.example.test.api.getErrorMessage
import com.example.test.data.Rates
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Query
import javax.inject.Inject

class ConverterUseCase@Inject constructor(
    private val apiManager: Service,
)  {
    fun getRates(base: String, symbols: String): Single<Rates> {
        return apiManager.rates(base, symbols)
            .flatMap {
                if (it.isSuccessful){
                    Single.just(it)
                }
                else{
                    Single.error(Throwable(getErrorMessage(it)))
                }
            }
            .map {
                it.body()!!
            }
            .observeOn(AndroidSchedulers.mainThread())
    }
}