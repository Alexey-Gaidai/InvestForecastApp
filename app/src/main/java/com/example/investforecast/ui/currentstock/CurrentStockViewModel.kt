package com.example.investforecast.ui.currentstock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.investforecast.domain.InvestRepository
import com.example.investforecast.domain.model.StockForecast
import com.example.investforecast.domain.model.StockInfo
import com.example.investforecast.domain.model.StockPrices
import com.example.investforecast.ui.stocks.StocksViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrentStockViewModel(private val investRepository: InvestRepository, val ticker: String) : ViewModel() {
    private val _stockPrices: MutableLiveData<List<StockPrices>> = MutableLiveData()
    val stockPrices: LiveData<List<StockPrices>> get() = _stockPrices
    private val _stockForecast: MutableLiveData<List<StockForecast>> = MutableLiveData()
    val stockForecast: LiveData<List<StockForecast>> get() = _stockForecast

    init {
        loadPrices()
        loadForecast()
    }

    private fun loadPrices() {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val data = investRepository.getStockPrices(ticker)
                _stockPrices.postValue(data)
            } catch (e: Throwable) {

            }
        }
    }
    private fun loadForecast() {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val data = investRepository.getStockForecast(ticker)
                _stockForecast.postValue(data)
            } catch (e: Throwable) {

            }
        }
    }
    fun addStock(count: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                investRepository.addStock(ticker, count)
            } catch (e: Throwable) {
                e.message?.let { Log.d("current", it) }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class CurrentStockViewModelFactory(
    private val investRepository: InvestRepository,
    private val ticker: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = CurrentStockViewModel(investRepository, ticker)
        return viewModel as T
    }
}
