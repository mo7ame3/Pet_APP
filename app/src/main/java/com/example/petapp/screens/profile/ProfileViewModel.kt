package com.example.petapp.screens.profile

import androidx.lifecycle.ViewModel
import com.example.petapp.data.WrapperClass
import com.example.petapp.model.delete.Delete
import com.example.petapp.model.profile.Profile
import com.example.petapp.repository.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: PetRepository) : ViewModel() {
    suspend fun profile(
        userId: String,
        authorization: String
    ): WrapperClass<Profile, Boolean, Exception> {
        return repository.getProfile(userId = userId, authorization = "Bearer $authorization")
    }

    suspend fun updateProfile(
        userId: String,
        authorization: String,
        name: String,
        city: String,
        phone: String,
        email: String
    ): WrapperClass<Profile, Boolean, Exception> {
        return repository.updateProfile(
            userId = userId,
            authorization = "Bearer $authorization",
            name = name,
            email = email,
            phone = phone,
            city = city
        )
    }

    suspend fun deleteUser(
        userId: String,
        authorization: String,
    ): WrapperClass<Delete, Boolean, Exception> {
        return repository.deleteUser(
            userId = userId,
            authorization = "Bearer $authorization",
        )
    }


}