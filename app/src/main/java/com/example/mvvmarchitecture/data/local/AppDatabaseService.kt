package com.example.mvvmarchitecture.data.local

import com.example.mvvmarchitecture.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

class AppDatabaseService constructor(private val appDatabase: AppDatabase) : DatabaseService {
    override fun getNewsArticles(): Flow<List<ArticleEntity>> {
        return appDatabase.newsArticlesDao().getNewsArticles()
    }

    override fun deleteAndInsertNewsArticles(articles: List<ArticleEntity>): List<Long> {
        return appDatabase.newsArticlesDao().deleteAndInsertAllNewsArticles(articles)
    }
}