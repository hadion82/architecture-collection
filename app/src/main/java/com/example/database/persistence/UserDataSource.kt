package com.example.database.persistence

import com.example.database.dao.UserDao
import com.example.database.entity.User

class UserDataSource(dao: UserDao): DataSource<UserDao, User>(dao) {

    fun getUser(userId: Long) =
        dao.getUser(userId)

    fun getUsers() =
        dao.getUsers()
}