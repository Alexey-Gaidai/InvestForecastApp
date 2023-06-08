package com.example.investforecast.data.nw.model


import com.google.gson.annotations.SerializedName

data class AuthInfo(
    @SerializedName("token")
    val token: String,
    @SerializedName("user_id")
    val userId: String
)