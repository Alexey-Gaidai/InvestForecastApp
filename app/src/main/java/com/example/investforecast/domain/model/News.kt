package com.example.investforecast.domain.model

import com.google.gson.annotations.SerializedName

data class News(
    val title: String,
    val imageUrl: String,
    val url: String,
    val industry: String?,
    val name: String?,
)