package com.example.investforecast

import android.content.SharedPreferences

class TokenManager(private val sharedPreferences: SharedPreferences) {

    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }

    private fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun clearToken() {
        val editor = sharedPreferences.edit()
        editor.remove("token")
        editor.apply()
    }

    fun isUserLoggedIn(): Boolean {
        return getToken() != null
    }
}