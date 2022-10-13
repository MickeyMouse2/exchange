package com.example.test.data


import com.google.gson.annotations.SerializedName

data class Rates(
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: RatesX
)