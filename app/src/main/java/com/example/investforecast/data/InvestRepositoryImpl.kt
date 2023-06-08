package com.example.investforecast.data

import android.util.Log
import com.example.investforecast.TokenManager
import com.example.investforecast.data.nw.InvestAPIService
import com.example.investforecast.data.nw.model.AddStockResponse
import com.example.investforecast.data.nw.model.toDomain
import com.example.investforecast.domain.InvestRepository
import com.example.investforecast.domain.model.StockForecast
import com.example.investforecast.domain.model.StockInfo
import com.example.investforecast.domain.model.StockPrices
import okhttp3.Credentials

class InvestRepositoryImpl(private val api: InvestAPIService): InvestRepository {
    override suspend fun login(username: String, password: String, tokenManager: TokenManager) {
        val credentials = Credentials.basic(username, password)

        try {
            val response = api.login(credentials)

            if (response.isSuccessful) {
                val authInfo = response.body()
                // Обработка успешного входа и получение токена и user_id из authInfo
                tokenManager.saveToken(authInfo!!.token)
            } else {
                // Обработка ошибки входа
            }
        } catch (e: Exception) {
            // Обработка сетевых ошибок
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

    override suspend fun addStock(token: String, ticker: String, count: Int): AddStockResponse {
        return try {

            val response = api.addStock(token, ticker, count.toString())

            if(response.isSuccessful){
                Log.d("current", "success")
                val result = response.body()!!
                result
            } else  {
                Log.d("current", "not success")
                AddStockResponse("ошибка")
            }
        } catch (e: Exception){
            Log.d("current", e.message.toString())
            AddStockResponse("что то пошло не так")
        }
    }
}