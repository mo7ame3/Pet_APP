package com.example.petapp.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.petapp.model.home.Data

class SharedViewModel : ViewModel() {

    var petDetails by mutableStateOf<Data?>(null)
        private set

    fun addPetDetails(item:Data){
        petDetails = item
    }

}