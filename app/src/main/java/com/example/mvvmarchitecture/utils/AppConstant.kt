package com.example.mvvmarchitecture.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

object AppConstant {

    const val API_KEY = "9f6482a584804376874b848980b7a044"
    const val COUNTRY = "us"
    const val FILE_COUNTRIES = "countries.json"
    const val FILE_LANGUAGES = "languages.json"


    sealed class NewsType : Parcelable {
        @Parcelize
        data class COUNTRY(var countryCode: String) : NewsType()

        @Parcelize
        data class SOURCE(var sourceId: String) : NewsType()

        @Parcelize
        data class LANGUAGE(var languageId: String) : NewsType()
    }

    val COUNTRIES_SUPPORTED_BY_NEWS_API = arrayListOf<String>(
        "ae",
        "ar",
        "at",
        "au",
        "be",
        "bg",
        "br",
        "ca",
        "ch",
        "cn",
        "co",
        "cu",
        "cz",
        "de",
        "eg",
        "fr",
        "gb",
        "gr",
        "hk",
        "hu",
        "id",
        "ie",
        "il",
        "in",
        "it",
        "jp",
        "kr",
        "lt",
        "lv",
        "ma",
        "mx",
        "my",
        "ng",
        "nl",
        "no",
        "nz",
        "ph",
        "pl",
        "pt",
        "ro",
        "rs",
        "ru",
        "sa",
        "se",
        "sg",
        "si",
        "sk",
        "th",
        "tr",
        "tw",
        "ua",
        "us",
        "ve",
        "za"
    )
}
