package com.example.mvvmarchitecture.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmarchitecture.data.local.dao.NewsArticleDao
import com.example.mvvmarchitecture.data.local.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun newsArticlesDao(): NewsArticleDao
}