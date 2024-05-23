package com.example.mvvmarchitecture.data.local

import com.example.mvvmarchitecture.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseService {
    fun getNewsArticles(): Flow<List<ArticleEntity>>
    fun deleteAndInsertNewsArticles(articles: List<ArticleEntity>):List<Long>

}