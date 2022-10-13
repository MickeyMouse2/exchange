package com.example.test.viewmodels.event

import com.example.test.data.Rates

sealed class CoursesEvent {
    class GetCourse(val rates: Rates): CoursesEvent()
    class ShowError(val message: String): CoursesEvent()
}