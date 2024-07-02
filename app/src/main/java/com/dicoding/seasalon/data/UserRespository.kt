package com.dicoding.seasalon.data

import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreferences: UserPreferences
){
    suspend fun saveSession(userModel: UserModel) {
        userPreferences.saveSession(userModel)
    }
    fun getSession(): Flow<UserModel> {
        return  userPreferences.getSession()
    }
    suspend fun logout() {
        userPreferences.logout()
    }

    companion object {
        fun getInstance(
            userPreference: UserPreferences
        ) = UserRepository(userPreference)

    }
}