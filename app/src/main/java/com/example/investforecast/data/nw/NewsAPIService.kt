package com.example.investforecast.data.nw

import com.example.investforecast.data.nw.model.NewsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.marketaux.com/"

interface NewsAPIService {
    companion object {
        private val logger: HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val okHttp =
            OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

        fun createApiService(): NewsAPIService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp)
                .build()
            return retrofit.create(NewsAPIService::class.java)
        }
    }

    @GET("/v1/news/all?")
    suspend fun getNews(
    @Query("countries") countries: String,
    @Query("filter_entities") filterEntities: String,
    @Query("language") language: String,
    @Query("api_token") api_token: String,
    @Query("industries") industry: String,
    @Query("page") page: Int,
    ): Response<NewsResponse>
}