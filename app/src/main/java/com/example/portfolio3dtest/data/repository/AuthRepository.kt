package com.example.portfolio3dtest.data.repository

import com.example.portfolio3dtest.PortfolioApplication
import com.example.portfolio3dtest.data.local.LocalDatabaseManager
import com.example.portfolio3dtest.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUser(): Flow<User>
    fun login(email: String, password: String): Boolean
    fun register(name: String, email: String, specialty: String): Boolean
}

class AuthRepositoryImpl(
    private val localDb: LocalDatabaseManager = PortfolioApplication.instance.localDatabase
) : AuthRepository {

    override fun getCurrentUser(): Flow<User> = localDb.userFlow

    override fun login(email: String, password: String): Boolean {
        if (email.isNotBlank() && password.isNotBlank()) {
            return true
        }
        return false
    }

    override fun register(name: String, email: String, specialty: String): Boolean {
        if (name.isNotBlank() && email.isNotBlank()) {
            localDb.saveUser(name, email, specialty)
            return true
        }
        return false
    }
}
