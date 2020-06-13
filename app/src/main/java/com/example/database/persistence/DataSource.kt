package com.example.database.persistence

import com.example.database.dao.ReactiveDao
import io.reactivex.Single

abstract class DataSource<DAO: ReactiveDao<T>, T>(val dao: DAO): ReactiveDao<T> {

    override fun insert(vararg values: T): List<Long> =
        dao.insert(*values)

    override fun insert(values: List<T>): List<Long> =
        dao.insert(values)

    override fun update(vararg values: T): Int =
        dao.update(*values)

    override fun update(values: List<T>): Int =
        dao.update(values)

    override fun delete(vararg values: T): Int =
        dao.delete(*values)

    override fun delete(values: List<T>): Int =
        dao.delete(values)

    override fun insertReactive(vararg values: T): Single<List<Long>> =
        dao.insertReactive(*values)

    override fun insertReactive(values: List<T>): Single<List<Long>> =
        dao.insertReactive(values)

    override fun updateReactive(vararg values: T): Single<Int> =
        dao.updateReactive(*values)

    override fun updateReactive(values: List<T>): Single<Int> =
        dao.updateReactive(values)

    override fun deleteReactive(vararg values: T): Single<Int> =
        dao.deleteReactive(*values)

    override fun deleteReactive(values: List<T>): Single<Int> =
        dao.deleteReactive(values)
}