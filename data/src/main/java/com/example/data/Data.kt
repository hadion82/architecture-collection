package com.example.data

import com.google.gson.annotations.SerializedName

data class Data<T>(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incomplete_results: Boolean,
    @SerializedName("items") val items: List<T>
)