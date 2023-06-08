package com.example.investforecast.domain

import com.example.investforecast.TokenManager
import com.example.investforecast.data.nw.model.AddStockResponse
import com.example.investforecast.domain.model.StockForecast
import com.example.investforecast.domain.model.StockInfo
import com.example.investforecast.domain.model.StockPrices

interface InvestRepository {
    suspend fun login(username: String, password: String, tokenManager: TokenManager)
    suspend fun getStocks(): List<StockInfo>
    suspend fun getStockPrices(ticker: String): List<StockPrices>
    suspend fun getStockForecast(ticker: String): List<StockForecast>
    suspend fun addStock(token: String, ticker: String, count: Int): AddStockResponse
}