package com.example.investforecast.domain.model


import com.google.gson.annotations.SerializedName

data class Portfolio(
    @SerializedName("investment_portfolio")
    val investmentPortfolio: InvestmentPortfolio
) {
    data class InvestmentPortfolio(
        @SerializedName("returns")
        val returns: Returns,
        @SerializedName("stocks")
        val stocks: List<Stock>,
        @SerializedName("total")
        val total: Double
    ) {
        data class Returns(
            @SerializedName("monetary_return")
            val monetaryReturn: Double,
            @SerializedName("percentage_return")
            val percentageReturn: Double
        )

        data class Stock(
            @SerializedName("count")
            val count: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("pricePerOne")
            val pricePerOne: Double,
            @SerializedName("ticker")
            val ticker: String,
            @SerializedName("total")
            val total: Double
        )
    }
}