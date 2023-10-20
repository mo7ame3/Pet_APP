package com.example.petapp.screens.pets

import androidx.lifecycle.ViewModel
import com.example.petapp.data.WrapperClass
import com.example.petapp.model.home.Home
import com.example.petapp.model.pets.GetAllPets
import com.example.petapp.repository.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetsViewModel @Inject constructor(private val repository: PetRepository):ViewModel() {

    suspend fun getMyPets(
        authorization: String
    ): WrapperClass<Home, Boolean, Exception> {
        return repository.getMyPets(authorization = "Bearer $authorization")
    }

    suspend fun getAllPets(): WrapperClass<GetAllPets, Boolean, Exception> {
        return repository.getAllPets()
    }



}