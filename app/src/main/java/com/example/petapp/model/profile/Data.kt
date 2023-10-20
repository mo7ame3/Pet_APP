package com.example.petapp.model.profile

data class Data(
    val city: String,
    val country: String,
    val email: String,
    val id: Int,
    val image_url: String,
    val name: String,
    val password: String,
    val password_confirm: String,
    val password_reset_code: Any,
    val password_reset_expire: Any,
    val phone: String,
    val role: String
)