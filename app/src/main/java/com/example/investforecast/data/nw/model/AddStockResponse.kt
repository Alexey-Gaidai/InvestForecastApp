package com.example.investforecast.data.nw.model


import com.google.gson.annotations.SerializedName

data class AddStockResponse(
    @SerializedName("message")
    val message: String
)