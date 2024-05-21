package com.example.mvvmarchitecture.data.model

import com.example.mvvmarchitecture.data.local.entity.SourceEntity
import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String = "",

    @SerializedName("category")
    var category: String,

    @SerializedName("country")
    var country: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("language")
    var language: String,

    @SerializedName("url")
    var url: String
)

fun Source.toSourceEntity(): SourceEntity {
    return SourceEntity(id = id, name = name)
}