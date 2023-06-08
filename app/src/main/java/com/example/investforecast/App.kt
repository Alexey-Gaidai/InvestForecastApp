package com.example.investforecast

import android.app.Application
import com.example.investforecast.data.InvestRepositoryImpl
import com.example.investforecast.data.nw.InvestAPIService
import com.example.investforecast.domain.InvestRepository

class App: Application() {
    private lateinit var apiService: InvestAPIService
    companion object {
        lateinit var repository: InvestRepository
            private set
    }

    override fun onCreate() {
        super.onCreate()
        initApiService()
        initRepository()
    }

    private fun initApiService() {
        apiService = InvestAPIService.createApiService()
    }

    private fun initRepository() {
        repository = InvestRepositoryImpl(apiService)
    }
}