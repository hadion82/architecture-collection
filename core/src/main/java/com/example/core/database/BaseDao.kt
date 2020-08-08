package com.example.core.database

import androidx.room.*


@Dao
interface BaseDao<T> : SingleDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg t: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg t: T)

    @Delete
    suspend fun delete(vararg t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(values: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(values: List<T>)

    @Delete
    suspend fun delete(values: List<T>)
}