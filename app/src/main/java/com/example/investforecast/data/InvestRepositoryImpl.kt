package com.example.investforecast.data

import android.util.Log
import com.example.investforecast.App
import com.example.investforecast.data.nw.InvestAPIService
import com.example.investforecast.data.nw.NewsAPIService
import com.example.investforecast.data.nw.model.AddStockResponse
import com.example.investforecast.data.nw.model.mapToDomain
import com.example.investforecast.data.nw.model.toDomain
import com.example.investforecast.domain.InvestRepository
import com.example.investforecast.domain.model.Forecast
import com.example.investforecast.domain.model.News
import com.example.investforecast.domain.model.Portfolio
import com.example.investforecast.domain.model.SignUp
import com.example.investforecast.domain.model.StockForecast
import com.example.investforecast.domain.model.StockInfo
import com.example.investforecast.domain.model.StockPrices
import okhttp3.Credentials

class InvestRepositoryImpl(private val api: InvestAPIService, private val newsApi: NewsAPIService): InvestRepository {
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

    override suspend fun signUp(name: String, lastname: String, email: String, password: String): String {
        try{

            val request = SignUp(name, lastname, email, password)
            val response = api.signUp(request)

            if (response.isSuccessful) {
                val registerResponse = response.body()
                if (registerResponse != null) {
                    return registerResponse.message
                }
            }
            Log.e("signup", "импл ${response.body()}")
            return "Failed to register user"
        }
        catch (e: Exception){
            Log.e("signup", "импл словила ${e.message.toString()}")
            return "something went wrong..."
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
                Log.d("result", response.body().toString())
                result
            } else  {
                Log.d("result is not success", "123")
                emptyList()
            }
        } catch (e: Exception){
            Log.d("exceptionnnnn", e.message.toString())
            emptyList()
        }
    }

    override suspend fun getStockForecast(ticker: String): Forecast {
        return try {
            val response = api.getStockForecast(ticker)

            if(response.isSuccessful){
                val result = response.body()!!
                result
            } else  {
                Forecast(emptyList(), emptyList(), emptyList())
            }
        } catch (e: Exception){
            Forecast(emptyList(), emptyList(), emptyList())
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
                Portfolio(Portfolio.InvestmentPortfolio( Portfolio.InvestmentPortfolio.Returns(0.0,0.0), emptyList(),0.0))
            }
        } catch (e: Exception){
            Portfolio(Portfolio.InvestmentPortfolio( Portfolio.InvestmentPortfolio.Returns(0.0,0.0), emptyList(),0.0))
        }
    }

    override suspend fun getNews(
        countries: String,
        filterEntities: String,
        language: String,
        api_token: String,
        industry: String,
        page: Int
    ): List<News> {
        return try {

            val response = newsApi.getNews(countries, filterEntities, language, api_token, industry, page)

            if(response.isSuccessful){
                val result = response.body()!!
                result.mapToDomain()
            } else  {
                emptyList()
            }
        } catch (e: Exception){
            emptyList()
        }
    }
}