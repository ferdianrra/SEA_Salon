package com.dicoding.seasalon.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.seasalon.data.UserModel
import com.dicoding.seasalon.data.UserRepository

class SplashScreenViewModel (val repository: UserRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

}