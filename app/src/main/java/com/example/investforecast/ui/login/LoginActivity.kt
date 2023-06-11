package com.example.investforecast.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.investforecast.App
import com.example.investforecast.MainActivity
import com.example.investforecast.R
import com.example.investforecast.databinding.ActivityLoginBinding
import com.example.investforecast.ui.login.signin.OnLoginSuccessListener
import com.example.investforecast.ui.login.signin.SignInFragment
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity(), OnLoginSuccessListener {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_login)
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.login_navigation)
        navController.graph = graph
    }

    override fun onLoginSuccess() {
        finish()
    }
}