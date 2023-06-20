package com.example.investforecast.domain.model


import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("linreg")
    val linreg: List<Linreg>,
    @SerializedName("prophet")
    val prophet: List<Prophet>,
    @SerializedName("rnn")
    val rnn: List<Rnn>
) {
    data class Linreg(
        @SerializedName("close")
        val close: Double,
        @SerializedName("date")
        val date: String
    )

    data class Prophet(
        @SerializedName("close")
        val close: Double,
        @SerializedName("date")
        val date: String
    )

    data class Rnn(
        @SerializedName("close")
        val close: Double,
        @SerializedName("date")
        val date: String
    )
}