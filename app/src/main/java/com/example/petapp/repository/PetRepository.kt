package com.example.petapp.repository

import retrofit2.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.petapp.data.WrapperClass
import com.example.petapp.model.authentication.Authentication
import com.example.petapp.model.delete.Delete
import com.example.petapp.model.home.Home
import com.example.petapp.model.pets.GetAllPets
import com.example.petapp.model.profile.Profile
import com.example.petapp.network.PetApi
import javax.inject.Inject

class PetRepository @Inject constructor(private val api: PetApi) {

    private val authentication: WrapperClass<Authentication, Boolean, Exception> = WrapperClass()
    private val home: WrapperClass<Home, Boolean, Exception> = WrapperClass()
    private val getAllPets: WrapperClass<GetAllPets, Boolean, Exception> = WrapperClass()
    private val profile: WrapperClass<Profile, Boolean, Exception> = WrapperClass()
    private val delete: WrapperClass<Delete, Boolean, Exception> = WrapperClass()

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
            authentication.data = Authentication(status = "fail", message = error.toString())
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
        city: String,
        password: String,
        passwordConfirm: String
    ): WrapperClass<Authentication, Boolean, Exception> {
        try {
            authentication.data = api.register(
                registerBody = mapOf(
                    "name" to name,
                    "phone" to phone,
                    "email" to email,
                    "city" to city,
                    "country" to "egypt",
                    "password" to password,
                    "password_confirm" to passwordConfirm
                )
            )
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            authentication.data = Authentication(status = "fail", message = error.toString())
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
            authentication.data = Authentication(status = "fail", message = error.toString())
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
                    "password_confirm" to passwordConfirm,
                )
            )
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            authentication.data = Authentication(status = "fail", message = error.toString())
        } catch (e: Exception) {
            Log.d("TAG", "resetPassword: $e")
            authentication.e = e
        }
        return authentication
    }

    suspend fun home(): WrapperClass<Home, Boolean, Exception> {
        try {
            home.data = api.home()
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            home.data = Home(status = "fail", message = error.toString())
        } catch (e: Exception) {
            Log.d("TAG", "home: $e")
            home.e = e
        }
        return home
    }

    suspend fun getProfile(
        userId: String,
        authorization: String
    ): WrapperClass<Profile, Boolean, Exception> {
        try {
            profile.data = api.getProfile(userId = userId, authorization = authorization)
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            profile.data = Profile(status = "fail", message = error.toString())
        } catch (e: Exception) {
            Log.d("TAG", "getProfile: $e")
            profile.e = e
        }
        return profile
    }

    suspend fun updateProfile(
        userId: String,
        authorization: String,
        name: String,
        email: String,
        city: String,
        phone: String
    ): WrapperClass<Profile, Boolean, Exception> {
        try {
            profile.data = api.updateProfile(
                userId = userId, authorization = authorization,
                updateProfile = mapOf(
                    "name" to name,
                    "email" to email,
                    "country" to "egypt",
                    "city" to city,
                    "phone" to phone
                )
            )
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            profile.data = Profile(status = "fail", message = error.toString())
        } catch (e: Exception) {
            Log.d("TAG", "updateProfile: $e")
            profile.e = e
        }
        return profile
    }

    suspend fun deleteUser(
        userId: String,
        authorization: String,
    ): WrapperClass<Delete, Boolean, Exception> {
        try {
            delete.data = api.deleteUser(
                userId = userId, authorization = authorization,
            )
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            delete.data = Delete(status = "fail", message = error.toString())
        } catch (e: NullPointerException) {
            delete.data = Delete(status = "success")
        } catch (e: Exception) {
            Log.d("TAG", "deleteUser: $e")
            delete.e = e
        }
        return delete
    }

    suspend fun getMyPets(authorization: String): WrapperClass<Home, Boolean, Exception> {
        try {
            home.data = api.getMyPets(authorization = authorization)
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            home.data = Home(status = "fail", message = error.toString())
        } catch (e: Exception) {
            Log.d("TAG", "myPets: $e")
            home.e = e
        }
        return home
    }

    suspend fun getAllPets(): WrapperClass<GetAllPets, Boolean, Exception> {
        try {
            getAllPets.data = api.getAllPets()
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            getAllPets.data = GetAllPets(status = "fail", message = error.toString())
        } catch (e: Exception) {
            Log.d("TAG", "getAllPets: $e")
            getAllPets.e = e
        }
        return getAllPets
    }


}