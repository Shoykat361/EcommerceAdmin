package com.example.ecommerceadmin.adapters

import androidx.lifecycle.ViewModel
import com.example.ecommerceadmin.repos.UserRepository
import com.google.firebase.auth.FirebaseAuth

class UserViewModel : ViewModel() {

    val userRepository = UserRepository()

    fun getAllUser() = userRepository.getAllUser()

}