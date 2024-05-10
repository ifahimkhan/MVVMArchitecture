package com.example.mvvmarchitecture.data.api

import com.example.mvvmarchitecture.data.model.NewsSourceResponse
import com.example.mvvmarchitecture.data.model.TopHeadlineResponse
import com.example.mvvmarchitecture.utils.AppConstant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {
    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String): TopHeadlineResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getNewsByCountry(@Query("country") country: String): TopHeadlineResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getNewsBySources(@Query("sources") source: String): TopHeadlineResponse

}