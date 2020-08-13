package com.example.core.database

import androidx.room.*


@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg values: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg values: T)

    @Delete
    suspend fun delete(vararg values: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(values: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(values: List<T>)

    @Delete
    suspend fun delete(values: List<T>)
}