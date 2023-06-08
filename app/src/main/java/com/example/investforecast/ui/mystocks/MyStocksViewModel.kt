package com.example.investforecast.ui.mystocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyStocksViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my stocks Fragment"
    }
    val text: LiveData<String> = _text
}