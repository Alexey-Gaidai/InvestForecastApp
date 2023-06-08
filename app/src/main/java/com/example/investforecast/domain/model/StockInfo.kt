package com.example.investforecast.domain.model


import com.google.gson.annotations.SerializedName

data class StockInfo(
    @SerializedName("_id")
    val id: Id,
    @SerializedName("lastPrice")
    val lastPrice: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("ticker")
    val ticker: String
) {
    data class Id(
        @SerializedName("\$oid")
        val oid: String
    )
}
