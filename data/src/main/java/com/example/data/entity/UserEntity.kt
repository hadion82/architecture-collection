package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class UserEntity(
        @PrimaryKey(autoGenerate = true)
        var _id: Int,
        @ColumnInfo(name = "id")
        val id: Long,
        @ColumnInfo(name = "query")
        var query: String?,
        @ColumnInfo(name = "user_name")
        @SerializedName(value = "login")
        val name: String?,
        @ColumnInfo(name = "avatar_url")
        @SerializedName(value = "avatar_url")
        val avatarUrl: String?,
        @ColumnInfo(name = "favorite")
        var favorite: Boolean = false
)