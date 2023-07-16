package com.example.petapp.screens.register

import androidx.lifecycle.ViewModel
import com.example.petapp.data.WrapperClass
import com.example.petapp.model.authentication.Authentication
import com.example.petapp.repository.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: PetRepository) : ViewModel() {
    suspend fun register(
        name: String,
        phone: String,
        email: String,
        city: String,
        password: String,
        passwordConfirm: String
    ): WrapperClass<Authentication, Boolean, Exception> {
        return repository.register(
            name = name,
            phone = "+20$phone",
            email = email,
            city = city,
            password = password,
            passwordConfirm = passwordConfirm
        )
    }
}