package com.example.data.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
        tableName = "users"
        /*foreignKeys = [
                ForeignKey(
                        entity = QueryEntity::class,
                        parentColumns = ["query"],
                        childColumns = ["query"],
                        onDelete = ForeignKey.CASCADE
                )]
        indices = [Index("query", "id", unique = true)] */
)
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
        @ColumnInfo(name = "html_url")
        @SerializedName(value = "html_url")
        val htmlUrl: String?,
        @ColumnInfo(name = "favorite")
        var favorite: Boolean = false
) {
        companion object {
                const val KEY_HTML_URL = "html_url"
        }
}