package com.example.investforecast.domain.model


import com.google.gson.annotations.SerializedName


data class StockPrices(
    @SerializedName("date")
    val date: String,
    @SerializedName("open")
    val open: Double,
    @SerializedName("high")
    val high: Double,
    @SerializedName("low")
    val low: Double,
    @SerializedName("close")
    val close: Double,
    @SerializedName("volume")
    val volume: Long,
)
