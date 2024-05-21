package com.example.mvvmarchitecture.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class SourceEntity(
    @PrimaryKey
    @ColumnInfo("source_id")
    var id: String?,
    @ColumnInfo("source_name")
    var name: String
)
