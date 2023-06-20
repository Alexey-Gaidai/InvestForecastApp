package com.example.investforecast.ui.mystocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investforecast.domain.InvestRepository
import com.example.investforecast.domain.model.Portfolio
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class MyStocksViewModel @Inject constructor(private val investRepository: InvestRepository) : ViewModel() {
    private val _portfolio: MutableLiveData<Portfolio> = MutableLiveData()
    val portfolio: LiveData<Portfolio> get() = _portfolio

    init {
        loadPortfolio()
    }

    fun loadPortfolio() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = investRepository.getPortfolio()
                _portfolio.postValue(data)
            } catch (e: Throwable) {

            }
        }
    }
}