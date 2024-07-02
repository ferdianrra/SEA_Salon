package com.dicoding.seasalon.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.seasalon.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileViewModel(val repository: UserRepository) : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name : LiveData<String> = _name
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()

    init {
        getName()
    }
    private fun getName() {
        val user = auth.currentUser
        _name.value = user?.displayName
    }

    fun logout() {
        auth.signOut()
        viewModelScope.launch {
            repository.logout()
        }
    }
}