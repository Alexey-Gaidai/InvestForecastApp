package com.example.investforecast.ui.login.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.investforecast.domain.InvestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val investRepository: InvestRepository): ViewModel() {
    private val _response: MutableLiveData<String> = MutableLiveData()
    val response: LiveData<String> get() = _response

    fun signUp(name: String, password: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response = investRepository.signUp(name,email, password)
                _response.postValue(response)
                Log.e("signup", "модель ответила $response")
            }
            catch (e: Throwable){
                Log.e("signup", "модель ответила ${e.message}")
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class SignUpViewModelFactory(
    private val investRepository: InvestRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = SignUpViewModel(investRepository)
        return viewModel as T
    }
}