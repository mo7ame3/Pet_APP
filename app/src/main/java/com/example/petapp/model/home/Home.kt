package com.example.petapp.model.home

data class Home(
    val data: List<Data>? =null,
    val length: Int = 0,
    val status: String,
    val message: String,
)