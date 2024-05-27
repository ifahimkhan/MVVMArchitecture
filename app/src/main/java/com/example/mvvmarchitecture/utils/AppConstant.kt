package com.example.mvvmarchitecture.utils

object AppConstant {

    const val BASE_URL = "https://newsapi.org/v2/"
    const val DEFAULT_COUNTRY = "us"
    const val FILE_COUNTRIES = "countries.json"
    const val FILE_LANGUAGES = "languages.json"

    const val DATABASE_NAME = "AppDatabase"

    object NewsBy {
        object IntentParam {
            object Key {
                const val NEWS_TYPE = "newsType"
                const val NEWS_TYPE_ID = "newsTypeId"
            }

            object Value {
                const val COUNTRY = "COUNTRY"
                const val SOURCE = "SOURCE"
                const val LANGUAGE = "LANGUAGE"
            }
        }
    }

    object Paging {
        const val INITIAL_PAGE = 1
        const val PAGE_SIZE = 10
    }

    object Worker {
        const val DAILY_NEWS_UPDATE = "PeriodicDailyNewsUpdate"
        const val DAILY_NEWS_UPDATE_HOURS = 6
    }

    object Notification {
        const val ID = 1
        const val KEY_ID = "NOTIFICATION_ID"

        object Content {
            const val TITLE = "RecentNews"
            const val DESCRIPTION = "Explore the latest breaking news!"

        }

        object Channel {
            const val ID = "DailyNewsChannel"
            const val NAME = "News"
            const val DESCRIPTION = "This channel is dedicated for breaking news update"
        }
    }

    interface ApiHeaders {
        interface Key {
            companion object {
                const val API_KEY = "X-Api-Key"
            }
        }

        interface Value {
            companion object {
                const val API_KEY = "c5fb8da0aa4c4e818e1db97a06162d7d"
            }
        }

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
