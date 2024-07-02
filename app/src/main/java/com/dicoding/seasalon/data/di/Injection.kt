package com.dicoding.seasalon.data.di

import android.content.Context
import com.dicoding.seasalon.data.UserPreferences
import com.dicoding.seasalon.data.UserRepository
import com.dicoding.seasalon.data.datastore


object Injection {

    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.datastore)
        return UserRepository.getInstance(pref)
    }

}