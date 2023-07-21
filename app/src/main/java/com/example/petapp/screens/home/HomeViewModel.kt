package com.example.petapp.screens.home

import androidx.lifecycle.ViewModel
import com.example.petapp.data.WrapperClass
import com.example.petapp.model.home.Home
import com.example.petapp.repository.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: PetRepository) : ViewModel() {
    suspend fun home(): WrapperClass<Home, Boolean, Exception> {
        return repository.home()
    }
}