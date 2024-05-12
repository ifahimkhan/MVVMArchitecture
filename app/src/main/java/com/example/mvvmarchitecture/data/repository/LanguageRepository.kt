package com.example.mvvmarchitecture.data.repository

import android.content.Context
import com.example.mvvmarchitecture.data.model.Language
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
class LanguageRepository @Inject constructor(@ApplicationContext private val context: Context) {
    fun getLanguages(): Flow<List<Language>> {
        return flow<List<Language>> {
            val data = IOUtils.readJsonFromAssets(context, AppConstant.FILE_LANGUAGES)
            val gson = Gson()
            emit(gson.fromJson(data, object : TypeToken<List<Language>>() {}.type))
        }.map { it }
    }
}