package com.example.investforecast.ui.currentstock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investforecast.domain.InvestRepository
import com.example.investforecast.domain.model.Forecast
import com.example.investforecast.domain.model.StockPrices
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Thread.sleep
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class CurrentStockViewModel @Inject constructor(private val investRepository: InvestRepository) :
    ViewModel() {
    private val _stockPrices: MutableLiveData<List<StockPrices>> = MutableLiveData()
    val stockPrices: LiveData<List<StockPrices>> get() = _stockPrices
    private val _stockForecast: MutableLiveData<Forecast> = MutableLiveData()
    val stockForecast: LiveData<Forecast> get() = _stockForecast
    private val _ticker: MutableLiveData<String> = MutableLiveData()
    val ticker: LiveData<String> get() = _ticker

    init {
        loadPrices()
    }

    private fun loadPrices() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = investRepository.getStockPrices(ticker.value!!)
                _stockPrices.postValue(data)
                sleep(1000)
                loadForecast()
            } catch (e: Throwable) {

            }
        }
    }

    private fun loadForecast() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = investRepository.getStockForecast(ticker.value!!)
                _stockForecast.postValue(data)
            } catch (e: Throwable) {

            }
        }
    }

    fun addStock(count: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                investRepository.addStock(ticker.value!!, count)
            } catch (e: Throwable) {
                e.message?.let { Log.d("current", it) }
            }
        }
    }

    fun setTicker(ticker: String) {
        _ticker.value = ticker
    }
}
