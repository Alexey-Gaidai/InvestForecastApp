package com.example.investforecast.data.nw.model


import com.google.gson.annotations.SerializedName


data class StockPrices(
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
    @SerializedName("date")
    val date: Date,
) {
    data class Date(
        @SerializedName("\$date")
        val date: String
    )
}

fun List<StockPrices>.toDomain(): List<com.example.investforecast.domain.model.StockPrices> {
    return map { stockPrices ->
        com.example.investforecast.domain.model.StockPrices(stockPrices.date.date,stockPrices.open, stockPrices.high, stockPrices.low, stockPrices.close, stockPrices.volume)
    }
}