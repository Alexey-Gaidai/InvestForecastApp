package com.example.investforecast.data.nw.model


import com.example.investforecast.domain.model.News
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("meta")
    val meta: Meta
) {
    data class Data(
        @SerializedName("description")
        val description: String,
        @SerializedName("entities")
        val entities: List<Entity>,
        @SerializedName("image_url")
        val imageUrl: String,
        @SerializedName("keywords")
        val keywords: String,
        @SerializedName("language")
        val language: String,
        @SerializedName("published_at")
        val publishedAt: String,
        @SerializedName("relevance_score")
        val relevanceScore: Any,
        @SerializedName("similar")
        val similar: List<Any>,
        @SerializedName("snippet")
        val snippet: String,
        @SerializedName("source")
        val source: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("uuid")
        val uuid: String
    ) {
        data class Entity(
            @SerializedName("country")
            val country: String,
            @SerializedName("exchange")
            val exchange: String,
            @SerializedName("exchange_long")
            val exchangeLong: String,
            @SerializedName("highlights")
            val highlights: List<Highlight>,
            @SerializedName("industry")
            val industry: String,
            @SerializedName("match_score")
            val matchScore: Double,
            @SerializedName("name")
            val name: String,
            @SerializedName("sentiment_score")
            val sentimentScore: Any,
            @SerializedName("symbol")
            val symbol: String,
            @SerializedName("type")
            val type: String
        ) {
            data class Highlight(
                @SerializedName("highlight")
                val highlight: String,
                @SerializedName("highlighted_in")
                val highlightedIn: String,
                @SerializedName("sentiment")
                val sentiment: Double
            )
        }
    }

    data class Meta(
        @SerializedName("found")
        val found: Int,
        @SerializedName("limit")
        val limit: Int,
        @SerializedName("page")
        val page: Int,
        @SerializedName("returned")
        val returned: Int
    )
}

private fun NewsResponse.Data.mapToDomain(): News {
    return News(
        title = this.title,
        imageUrl = this.imageUrl,
        url = this.url,
        industry = this.entities[0].industry,
        name = this.entities[0].name
    )
}

fun NewsResponse.mapToDomain(): List<News> {
    return this.data.map { newsData ->
        newsData.mapToDomain()
    }
}