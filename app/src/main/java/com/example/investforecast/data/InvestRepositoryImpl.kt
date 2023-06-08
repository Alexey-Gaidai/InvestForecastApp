package com.example.investforecast.data

import com.example.investforecast.App
import com.example.investforecast.TokenManager
import com.example.investforecast.data.nw.InvestAPIService
import com.example.investforecast.data.nw.model.AddStockResponse
import com.example.investforecast.data.nw.model.toDomain
import com.example.investforecast.domain.InvestRepository
import com.example.investforecast.domain.model.Portfolio
import com.example.investforecast.domain.model.StockForecast
import com.example.investforecast.domain.model.StockInfo
import com.example.investforecast.domain.model.StockPrices
import okhttp3.Credentials

class InvestRepositoryImpl(private val api: InvestAPIService): InvestRepository {
    override suspend fun login(username: String, password: String): Boolean {
        val credentials = Credentials.basic(username, password)

        return try {
            val response = api.login(credentials)

            if (response.isSuccessful) {
                val authInfo = response.body()

                App.tokenManager.clearToken()
                App.tokenManager.saveToken(authInfo!!.token)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getStocks(): List<StockInfo> {
        return try {
            val response = api.getStockInfoList()

            if (response.isSuccessful) {
                response.body()!!
            } else{
                emptyList()
            }
        } catch (e: Exception){
            emptyList()
        }
    }

    override suspend fun getStockPrices(ticker: String): List<StockPrices> {
        return try {
            val response = api.getStockPrices(ticker)

            if(response.isSuccessful){
                val result = response.body()!!
                result.toDomain()
            } else  {
                emptyList()
            }
        } catch (e: Exception){
            emptyList()
        }
    }

    override suspend fun getStockForecast(ticker: String): List<StockForecast> {
        return try {
            val response = api.getStockForecast(ticker)

            if(response.isSuccessful){
                val result = response.body()!!
                result
            } else  {
                emptyList()
            }
        } catch (e: Exception){
            emptyList()
        }
    }

    override suspend fun addStock(ticker: String, count: Int): AddStockResponse {
        return try {

            val response = api.addStock(App.tokenManager.getToken()!!,ticker, count.toString())

            if(response.isSuccessful){
                val result = response.body()!!
                result
            } else  {
                AddStockResponse("ошибка")
            }
        } catch (e: Exception){
            AddStockResponse("что то пошло не так")
        }
    }

    override suspend fun getPortfolio(): Portfolio {
        return try {

            val response = api.getPortfolio(App.tokenManager.getToken()!!)

            if(response.isSuccessful){
                val result = response.body()!!
                result
            } else  {
                Portfolio(Portfolio.InvestmentPortfolio( 0.0, emptyList(),0.0))
            }
        } catch (e: Exception){
            Portfolio(Portfolio.InvestmentPortfolio( 0.0, emptyList(),0.0))
        }
    }
}