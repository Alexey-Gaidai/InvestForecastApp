package com.example.investforecast.ui.login.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investforecast.domain.InvestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(private val investRepository: InvestRepository) :
    ViewModel() {
    private val _response: MutableLiveData<String> = MutableLiveData()
    val response: LiveData<String> get() = _response

    fun signUp(name: String, lastname: String, password: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = investRepository.signUp(name, lastname, email, password)
                _response.postValue(response)
                Log.e("signup", "модель ответила $response")
            } catch (e: Throwable) {
                Log.e("signup", "модель ответила ${e.message}")
            }
        }
    }

}