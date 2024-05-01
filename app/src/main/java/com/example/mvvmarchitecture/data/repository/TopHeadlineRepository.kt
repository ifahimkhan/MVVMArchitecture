package com.example.mvvmarchitecture.data.repository

import com.example.mvvmarchitecture.data.api.NetworkService
import com.example.mvvmarchitecture.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService){
    fun getTopHeadLines(country:String): Flow<List<Article>>{
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map { it.articles }

    }
}