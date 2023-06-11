package com.example.investforecast.data.nw

import com.example.investforecast.data.nw.model.AddStockResponse
import com.example.investforecast.data.nw.model.AuthInfo
import com.example.investforecast.data.nw.model.SignUpResponse
import com.example.investforecast.data.nw.model.StockPrices
import com.example.investforecast.domain.model.Portfolio
import com.example.investforecast.domain.model.SignUp
import com.example.investforecast.domain.model.StockForecast
import com.example.investforecast.domain.model.StockInfo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "http://192.168.1.184:5000/"

interface InvestAPIService {
    companion object {
        private val logger: HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val okHttp =
            OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

        fun createApiService(): InvestAPIService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp)
                .build()
            return retrofit.create(InvestAPIService::class.java)
        }
    }

    @POST("/login")
    suspend fun login(
        @Header("Authorization") authorization: String
    ): Response<AuthInfo>

    @GET("/stocks")
    suspend fun getStockInfoList(): Response<List<StockInfo>>

    @GET("/stocks/{ticker}")
    suspend fun getStockPrices(
        @Path("ticker") ticker: String
    ): Response<List<StockPrices>>

    @GET("/stocks/{ticker}/forecast")
    suspend fun getStockForecast(
        @Path("ticker") ticker: String
    ): Response<List<StockForecast>>

    @POST("/add_stock")
    suspend fun addStock(
        @Header("Authorization") token: String,
        @Query("ticker") ticker: String,
        @Query("quantity") qty: String
    ): Response<AddStockResponse>

    @GET("/portfolio")
    suspend fun getPortfolio(
        @Header("Authorization") token: String
    ): Response<Portfolio>

    @POST("/register")
    suspend fun signUp(
        @Body userData: SignUp
    ): Response<SignUpResponse>
}