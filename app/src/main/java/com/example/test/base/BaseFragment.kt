package com.example.test.base

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<T: ViewBinding>: Fragment() {
    open var views: T? = null

    fun withViews(action: T.() -> Unit) {
        views?.let(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        views = null
    }

    val attachViews: (T) -> Unit
        get() = { views = it }
}