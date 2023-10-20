package com.example.petapp.model.pets

data class Field(
    val columnID: Int,
    val dataTypeID: Int,
    val dataTypeModifier: Int,
    val dataTypeSize: Int,
    val format: String,
    val name: String,
    val tableID: Int
)