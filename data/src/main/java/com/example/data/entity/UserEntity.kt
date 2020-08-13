package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class UserEntity(
        @PrimaryKey
        val id: Long,
        @ColumnInfo(name = "user_name")
        @SerializedName(value = "login")
        val name: String?,
        @ColumnInfo(name = "avatar_url")
        @SerializedName(value = "avatar_url")
        val avatarUrl: String?,
        @ColumnInfo(name = "favorite")
        var favorite: Boolean = false
)