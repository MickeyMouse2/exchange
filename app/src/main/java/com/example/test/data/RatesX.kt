package com.example.test.data


import com.google.gson.annotations.SerializedName

data class RatesX(
    @SerializedName("BGN")
    val gBP: Double,
    @SerializedName("EUR")
    val eur: Double,
    @SerializedName("USD")
    val uSD: Double
)