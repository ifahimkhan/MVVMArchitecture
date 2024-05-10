package com.example.mvvmarchitecture.data.model

import com.google.gson.annotations.SerializedName

data class NewsSourceResponse(
    @SerializedName("status")
    var status: String,
    @SerializedName("sources")
    var sources: List<Source> = arrayListOf()
)
