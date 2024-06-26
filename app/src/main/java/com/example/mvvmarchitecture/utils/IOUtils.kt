package com.example.mvvmarchitecture.utils

import android.content.Context

object IOUtils {
    fun readJsonFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}