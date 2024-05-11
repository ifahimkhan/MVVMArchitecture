package com.example.mvvmarchitecture.data.repository

import android.content.Context
import android.util.Log
import com.example.mvvmarchitecture.data.model.Country
import com.example.mvvmarchitecture.di.ApplicationContext
import com.example.mvvmarchitecture.utils.AppConstant
import com.example.mvvmarchitecture.utils.IOUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountriesRepository @Inject constructor(@ApplicationContext private val context: Context) {
    fun getCountries(): Flow<List<Country>> {
        return flow<List<Country>> {
            val data = IOUtils.readJsonFromAssets(context, AppConstant.FILE_COUNTRIES)
            val gson = Gson()
            Log.d("TAG", "getCountries: " + data)
            emit(gson.fromJson(data, object : TypeToken<List<Country>>() {}.type))
        }.map { it -> it.filter { it.code.lowercase() in AppConstant.COUNTRIES_SUPPORTED_BY_NEWS_API } }
    }
}