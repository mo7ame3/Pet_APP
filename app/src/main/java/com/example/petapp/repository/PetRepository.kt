package com.example.petapp.repository

import retrofit2.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.petapp.data.WrapperClass
import com.example.petapp.model.authentication.Authentication
import com.example.petapp.network.PetApi
import javax.inject.Inject

class PetRepository @Inject constructor(private val api: PetApi) {

    private val authentication: WrapperClass<Authentication, Boolean, Exception> = WrapperClass()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun login(
        email: String,
        password: String
    ): WrapperClass<Authentication, Boolean, Exception> {
        try {
            authentication.data = api.login(
                loginBody = mapOf(
                    "email" to email,
                    "password" to password
                )
            )
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            authentication.data = Authentication(status = "success", message = error.toString())
        } catch (e: Exception) {
            Log.d("TAG", "login: $e")
            authentication.e = e
        }
        return authentication
    }


    suspend fun register(
        name: String,
        phone: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): WrapperClass<Authentication, Boolean, Exception> {
        try {
            authentication.data = api.register(
                registerBody = mapOf(
                    "name" to name,
                    "phone" to phone,
                    "email" to email,
                    "password" to password,
                    "passwordConfirm" to passwordConfirm
                )
            )
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            authentication.data = Authentication(status = "success", message = error.toString())
        } catch (e: Exception) {
            Log.d("TAG", "register: $e")
            authentication.e = e
        }
        return authentication
    }

    suspend fun forgetPassword(
        email: String,
    ): WrapperClass<Authentication, Boolean, Exception> {
        try {
            authentication.data = api.forgetPassword(
                forgetPasswordBody = mapOf(
                    "email" to email,
                )
            )
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            authentication.data = Authentication(status = "success", message = error.toString())
        } catch (e: Exception) {
            Log.d("TAG", "forgetPassword: $e")
            authentication.e = e
        }
        return authentication
    }

    suspend fun resetPassword(
        password: String,
        passwordConfirm: String,
        code: String
    ): WrapperClass<Authentication, Boolean, Exception> {
        try {
            authentication.data = api.resetPassword(
                resetPasswordBody = mapOf(
                    "code" to code,
                    "password" to password,
                    "passwordConfirm" to passwordConfirm,
                )
            )
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            authentication.data = Authentication(status = "success", message = error.toString())
        } catch (e: Exception) {
            Log.d("TAG", "resetPassword: $e")
            authentication.e = e
        }
        return authentication
    }

}