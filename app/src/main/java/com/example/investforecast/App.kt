package com.example.investforecast

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.investforecast.data.InvestRepositoryImpl
import com.example.investforecast.data.nw.InvestAPIService
import com.example.investforecast.data.nw.NewsAPIService
import com.example.investforecast.domain.InvestRepository

class App : Application() {
    private lateinit var apiService: InvestAPIService
    private lateinit var newsApiService: NewsAPIService

    companion object {
        lateinit var repository: InvestRepository
            private set
        lateinit var tokenManager: TokenManager
            private set
        lateinit var sharedPreferences: SharedPreferences
            private set
    }

    override fun onCreate() {
        super.onCreate()
        initApiService()
        initNewsApiService()
        initRepository()
        initSharedPreferences()
        initTokenManager()
    }

    private fun initTokenManager() {
        tokenManager = TokenManager(sharedPreferences)
    }

    private fun initSharedPreferences() {
        sharedPreferences = getSharedPreferences("UserSharedPreferences", Context.MODE_PRIVATE)
    }

    private fun initApiService() {
        apiService = InvestAPIService.createApiService()
    }
    private fun initNewsApiService() {
        newsApiService = NewsAPIService.createApiService()
    }

    private fun initRepository() {
        repository = InvestRepositoryImpl(apiService, newsApiService)
    }
}