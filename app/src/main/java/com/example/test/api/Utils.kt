package com.example.test.api

import org.json.JSONObject
import retrofit2.Response

fun <T>getErrorMessage(response: Response<T>): String{
    val jObjError = JSONObject(response.errorBody()!!.string())

    return jObjError.getString("message")
}