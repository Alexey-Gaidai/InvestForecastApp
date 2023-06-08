package com.example.investforecast

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.investforecast.data.nw.InvestAPIService
import com.example.investforecast.databinding.ActivityMainBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("UserSharedPreferences", Context.MODE_PRIVATE)
        tokenManager = TokenManager(sharedPreferences)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_stocks, R.id.navigation_mystocks
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val isLoggedIn = checkIfUserIsLoggedIn() // Проверяем, аутентифицирован ли пользователь

        if (!isLoggedIn) {
            // Если пользователь не аутентифицирован, открываем страницу входа
            navController.navigate(R.id.loginFragment)
        }
    }

    fun checkIfUserIsLoggedIn(): Boolean {
        return tokenManager.isUserLoggedIn()
    }
}