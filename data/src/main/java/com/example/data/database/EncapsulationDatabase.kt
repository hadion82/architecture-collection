package com.example.data.database

import com.example.data.dao.UserDao
import javax.inject.Inject

/**
 * All modules must be accessible for dependency injection.
 * The lack of access restrictions can cause problems that break the Clean Architecture dependency with direct use.
 * This class serves to avoid conflicts between the restriction of direct access and the infusion of dependencies.
 * A passageway for receiving and internally using open-dependent objects.
 */

internal class EncapsulationDatabase @Inject constructor(
    private val database: LocalDataBase
){
    fun getUserDao(): UserDao = database.getUserDao()
}