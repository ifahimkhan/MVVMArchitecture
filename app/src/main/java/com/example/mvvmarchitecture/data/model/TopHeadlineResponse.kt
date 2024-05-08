package com.example.mvvmarchitecture.data.model

import com.google.gson.annotations.SerializedName


data class TopHeadlineResponse(
    @SerializedName("articles")
    val articles: List<Article> = listOf(),
    @SerializedName("status")
    val status: String = "",
    @SerializedName("totalResults")
    val totalResults: Int = 0
)

data class Article(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("source")
    val source: Source,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("urlToImage")
    val urlToImage: String = ""
)

data class Source(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String = "",

    @SerializedName("category")
    var category: String,

    @SerializedName("country")
    var country: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("language")
    var language: String,

    @SerializedName("url")
    var url: String
)