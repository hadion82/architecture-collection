package com.example.core.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Single

interface SingleDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFlow(vararg values : T): Single<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFlow(values : List<T>): Single<List<Long>>

    @Update
    fun updateFlow(vararg values : T): Single<Int>

    @Update
    fun updateFlow(values : List<T>): Single<Int>

    @Delete
    fun deleteFlow(vararg values : T): Single<Int>

    @Delete
    fun deleteFlow(values : List<T>): Single<Int>
}