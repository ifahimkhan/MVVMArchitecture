package com.example.mvvmarchitecture.data.model

import com.example.mvvmarchitecture.data.local.entity.ArticleEntity
import com.google.gson.annotations.SerializedName

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

fun Article.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        title = title,
        description = description,
        url = url,
        imageUrl = urlToImage,
        source = source.toSourceEntity()
    )
}

public fun List<Article>.toArticleList(): List<ArticleEntity> {
    return map {
        it.toArticleEntity()
    }

}