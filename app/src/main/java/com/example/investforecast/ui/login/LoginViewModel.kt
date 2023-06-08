package com.example.investforecast.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.investforecast.TokenManager
import com.example.investforecast.domain.InvestRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val investRepository: InvestRepository, private val tokenManager: TokenManager) : ViewModel() {

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                investRepository.login(username, password, tokenManager)
            } catch (e: Exception) {
                // Обработка ошибок входа или сетевых ошибок
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val investRepository: InvestRepository,
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = LoginViewModel(investRepository, tokenManager)
        return viewModel as T
    }
}