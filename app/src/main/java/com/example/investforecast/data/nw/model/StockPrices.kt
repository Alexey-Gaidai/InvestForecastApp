package com.example.investforecast.data.nw.model


import com.google.gson.annotations.SerializedName


data class StockPrices(
    @SerializedName("close")
    val close: Double,
    @SerializedName("date")
    val date: Date
) {
    data class Date(
        @SerializedName("\$date")
        val date: String
    )
}

fun List<StockPrices>.toDomain(): List<com.example.investforecast.domain.model.StockPrices> {
    return map { stockPrices ->
        com.example.investforecast.domain.model.StockPrices(stockPrices.close, stockPrices.date.date)
    }
}