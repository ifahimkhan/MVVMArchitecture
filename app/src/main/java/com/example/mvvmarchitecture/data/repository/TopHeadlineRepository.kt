package com.example.mvvmarchitecture.data.repository

import com.example.mvvmarchitecture.data.api.NetworkService
import com.example.mvvmarchitecture.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {
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
}