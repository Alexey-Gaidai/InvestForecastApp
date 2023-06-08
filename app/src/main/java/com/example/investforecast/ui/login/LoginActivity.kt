package com.example.investforecast.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.investforecast.App
import com.example.investforecast.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val model: LoginActivityViewModel by viewModels{ LoginActivityViewModelFactory(App.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            login()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        model.isTokenNotEmpty.observe(this){
            it.getContentIfNotHandled()?.let{
                Snackbar.make(binding.root, "Вход выполнен", Snackbar.LENGTH_LONG).show()
            }
            finish()
        }
    }

    private fun login() {
        model.login(binding.etLogin.text.toString(), binding.etPassword.text.toString())
    }
}