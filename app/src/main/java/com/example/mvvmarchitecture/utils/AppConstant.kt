package com.example.mvvmarchitecture.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

object AppConstant {

    const val API_KEY = "9f6482a584804376874b848980b7a044"
    const val COUNTRY = "us"
    const val FILE_COUNTRIES = "countries.json"

    sealed class NewsType : Parcelable {
        @Parcelize
        data class COUNTRY(var code: String) : NewsType()

        @Parcelize
        data class SOURCE(var id: String) : NewsType()

        @Parcelize
        data class LANGUAGE(var language: String) : NewsType()
    }
}
