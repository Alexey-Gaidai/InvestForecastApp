package com.example.investforecast.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investforecast.domain.InvestRepository
import com.example.investforecast.domain.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TOKEN = "WRnJQdTExQZtL2d3KfQ0pgnWXNKZtbuRW4Uxdb6O"
private const val COUNTRIES = "ru"
private const val LANGUAGE = "ru"
private const val FILTERED_ENTITIES = "true"
private const val INDUSTRY_DEFAULT = ""
private const val PAGE = 1

@HiltViewModel
class HomeViewModel @Inject constructor(private val investRepository: InvestRepository) :
    ViewModel() {
    private val _news: MutableLiveData<List<News>> = MutableLiveData()
    val news: LiveData<List<News>> get() = _news

    init {
        loadNews(INDUSTRY_DEFAULT, PAGE)
    }

    fun loadNews(
        industry: String,
        page: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = investRepository.getNews(
                    COUNTRIES, FILTERED_ENTITIES,
                    LANGUAGE, TOKEN, industry, page
                )
                _news.postValue(data)
            } catch (e: Throwable) {

            }
        }
    }
}