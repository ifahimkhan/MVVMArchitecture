package com.example.mvvmarchitecture.data.repository

import com.example.mvvmarchitecture.data.api.NetworkService
import com.example.mvvmarchitecture.data.local.DatabaseService
import com.example.mvvmarchitecture.data.local.entity.ArticleEntity
import com.example.mvvmarchitecture.data.model.Article
import com.example.mvvmarchitecture.data.model.toArticleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {
    fun getTopHeadlines(country: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map { it.articles }

    }

    fun getNewsBySource(source: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getNewsBySources(source))
        }.map { it.articles }
    }

    fun getNewsByCountry(country: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getNewsByCountry(country))
        }.map { it.articles }
    }

    fun getNewsByLanguage(language: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getNewsByLanguage(language))
        }.map { it.articles }
    }

    fun getNewsBySearch(phrase: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getNewsBySearch(phrase))
        }.map { it.articles }

    }

    fun getNewsArticles(country: String): Flow<List<ArticleEntity>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles.map { article -> article.toArticleEntity() }
        }.flatMapConcat { articles ->
            flow {
                emit(databaseService.deleteAndInsertNewsArticles(articles))
            }
        }.flatMapConcat { databaseService.getNewsArticles() }
    }

    fun getNewsArticleDirectFromDb(country: String): Flow<List<ArticleEntity>> {
        return databaseService.getNewsArticles()
    }
}