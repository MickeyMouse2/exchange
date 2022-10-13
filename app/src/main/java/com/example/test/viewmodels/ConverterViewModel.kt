package com.example.test.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.test.base.BaseViewModel
import com.example.test.usecase.ConverterUseCase
import com.example.test.viewmodels.event.CoursesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val converterUseCase: ConverterUseCase
) : BaseViewModel() {

    val event = MutableLiveData<CoursesEvent>()

    fun startCourseUpdate(base: String, symbols: String) {
        Observable.timer(5000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                getCourses(base, symbols)
            }
    }

    fun getCourses(base: String, symbols: String) {
        onCleared()
        addDisposable(
            converterUseCase.getRates(base, symbols)
                .subscribe({
                    if (it.rates != null) {
                        event.postValue(CoursesEvent.GetCourse(it))
                    } else {
                        event.postValue(CoursesEvent.ShowError("something went wrong"))
                    }
                }, {
                    event.postValue(CoursesEvent.ShowError(it.message!!))
                })
        )
        startCourseUpdate(base, symbols)
    }
}