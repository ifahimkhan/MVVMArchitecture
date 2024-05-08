package com.example.mvvmarchitecture.data.repository

import com.example.mvvmarchitecture.data.api.NetworkService
import com.example.mvvmarchitecture.data.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsSourcesRepository @Inject constructor(private val networkService: NetworkService) {

    fun getNewsSource(): Flow<List<Source>> {
        return flow {
            emit(networkService.getNewsSources())
        }.map { it.sources }
    }
}