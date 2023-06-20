package com.example.investforecast

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.investforecast.data.InvestRepositoryImpl
import com.example.investforecast.data.nw.InvestAPIService
import com.example.investforecast.data.nw.NewsAPIService
import com.example.investforecast.domain.InvestRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var tokenManager: TokenManager
            private set
        lateinit var sharedPreferences: SharedPreferences
            private set
    }

    override fun onCreate() {
        super.onCreate()
        initSharedPreferences()
        initTokenManager()
    }

    private fun initTokenManager() {
        tokenManager = TokenManager(sharedPreferences)
    }

    private fun initSharedPreferences() {
        sharedPreferences = getSharedPreferences("UserSharedPreferences", Context.MODE_PRIVATE)
    }
}