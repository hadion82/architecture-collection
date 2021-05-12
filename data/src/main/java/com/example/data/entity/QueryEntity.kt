package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_query")
data class QueryEntity(
    @PrimaryKey
    val name_query: String,
    @ColumnInfo(name = "page")
    val page: Int
)