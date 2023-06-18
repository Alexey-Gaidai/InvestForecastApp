package com.example.investforecast.domain.model


import com.google.gson.annotations.SerializedName

data class StockInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("lastPrice")
    val lastPrice: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("ticker")
    val ticker: String
)
