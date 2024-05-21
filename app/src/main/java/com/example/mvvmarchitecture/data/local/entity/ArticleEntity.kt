package com.example.mvvmarchitecture.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NewsArticles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo("title") val title: String = "",
    @ColumnInfo("description") val description: String = "",
    @ColumnInfo("url") val url: String = "",
    @ColumnInfo("imageUrl") val imageUrl: String? = "",
    @ColumnInfo("language") val language: String = "",
    @ColumnInfo("country") val country: String = "",
    @Embedded val source: SourceEntity
)