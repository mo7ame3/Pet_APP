package com.example.petapp.screens.login

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import com.example.petapp.data.WrapperClass
import com.example.petapp.model.authentication.Authentication
import com.example.petapp.repository.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: PetRepository) : ViewModel() {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun login(email : String, password : String): WrapperClass<Authentication, Boolean, Exception> {
        return repository.login(
            email = email , password = password
        )
    }
}