package com.example.investforecast.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.investforecast.TokenManager
import com.example.investforecast.domain.InvestRepository
import com.example.investforecast.ui.generic.Event
import com.example.investforecast.ui.stocks.StocksViewModel
import kotlin.math.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivityViewModel(private val investRepository: InvestRepository): ViewModel() {
    private val _isTokenNotEmpty: MutableLiveData<Event<Unit>> = MutableLiveData()
    val isTokenNotEmpty: LiveData<Event<Unit>> get() = _isTokenNotEmpty

    fun login(login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val isOk = investRepository.login(login, password)
                if (isOk) {
                    _isTokenNotEmpty.postValue(Event(Unit))
                }
            } catch (e: Throwable) {

            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class LoginActivityViewModelFactory(
    private val investRepository: InvestRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = LoginActivityViewModel(investRepository)
        return viewModel as T
    }
}