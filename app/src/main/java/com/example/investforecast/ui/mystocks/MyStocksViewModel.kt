package com.example.investforecast.ui.mystocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.investforecast.domain.InvestRepository
import com.example.investforecast.domain.model.Portfolio
import com.example.investforecast.domain.model.StockInfo
import com.example.investforecast.ui.stocks.StocksViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyStocksViewModel(private val investRepository: InvestRepository) : ViewModel() {
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

@Suppress("UNCHECKED_CAST")
class MyStocksViewModelFactory(
    private val investRepository: InvestRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = MyStocksViewModel(investRepository)
        return viewModel as T
    }
}