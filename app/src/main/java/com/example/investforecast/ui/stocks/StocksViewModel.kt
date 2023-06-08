package com.example.investforecast.ui.stocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.investforecast.TokenManager
import com.example.investforecast.domain.InvestRepository
import com.example.investforecast.domain.model.StockInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class StocksViewModel(private val investRepository: InvestRepository) : ViewModel() {

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

@Suppress("UNCHECKED_CAST")
class StocksViewModelFactory(
    private val investRepository: InvestRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = StocksViewModel(investRepository)
        return viewModel as T
    }
}