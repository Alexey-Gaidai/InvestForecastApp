package com.example.investforecast.ui.stocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investforecast.domain.InvestRepository
import com.example.investforecast.domain.model.StockInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class StocksViewModel @Inject constructor(private val investRepository: InvestRepository) : ViewModel() {

    private val _stocks: MutableLiveData<List<StockInfo>> = MutableLiveData()
    val stocks: LiveData<List<StockInfo>> get() = _stocks

    init {
        loadStocks()
    }

    private fun loadStocks() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = investRepository.getStocks()
                _stocks.postValue(data)
            } catch (e: Throwable) {

            }
        }
    }
}