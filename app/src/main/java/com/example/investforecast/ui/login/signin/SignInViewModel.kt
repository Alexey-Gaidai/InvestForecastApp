package com.example.investforecast.ui.login.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investforecast.domain.InvestRepository
import com.example.investforecast.ui.generic.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class SignInViewModel @Inject constructor(private val investRepository: InvestRepository) :
    ViewModel() {
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