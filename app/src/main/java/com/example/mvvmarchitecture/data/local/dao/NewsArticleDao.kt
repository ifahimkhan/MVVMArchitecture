package com.example.mvvmarchitecture.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.mvvmarchitecture.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticleDao {
    @Transaction
    @Query("SELECT * FROM NewsArticles")
    fun getNewsArticles(): Flow<List<ArticleEntity>>

    @Insert
    fun insertNewsArticles(articles: List<ArticleEntity>): List<Long>

    @Query("Delete from NewsArticles")
    fun deleteArticles()

    @Transaction
    fun deleteAndInsertAllNewsArticles(articles: List<ArticleEntity>): List<Long> {
        deleteArticles()
        return insertNewsArticles(articles)
    }
}