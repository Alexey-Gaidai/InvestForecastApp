package com.example.investforecast.domain.model


import com.google.gson.annotations.SerializedName

data class StockForecast(
    @SerializedName("close")
    val close: Double,
    @SerializedName("date")
    val date: String
)