package com.example.petapp.network


import com.example.petapp.constant.Constant
import com.example.petapp.model.authentication.Authentication
import com.example.petapp.model.delete.Delete
import com.example.petapp.model.home.Home
import com.example.petapp.model.profile.Profile
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PetApi {

    @POST(Constant.LOGIN)
    suspend fun login(
        @Body loginBody: Map<String, String>
    ): Authentication

    @POST(Constant.REGISTER)
    suspend fun register(
        @Body registerBody: Map<String, String>
    ): Authentication

    @POST(Constant.FORGET_PASSWORD)
    suspend fun forgetPassword(
        @Body forgetPasswordBody: Map<String, String>
    ): Authentication

    @POST(Constant.RESET_PASSWORD)
    suspend fun resetPassword(
        @Body resetPasswordBody: Map<String, String>
    ): Authentication

    @GET(Constant.Home)
    suspend fun home(): Home

    @GET(Constant.GET_USER + "/{userId}")
    suspend fun getProfile(
        @Path("userId") userId: String,
        @Header("Authorization") authorization: String
    ): Profile

    @PATCH(Constant.GET_USER + "/{userId}")
    suspend fun updateProfile(
        @Path("userId") userId: String,
        @Header("Authorization") authorization: String,
        @Body updateProfile: Map<String, String>
    ): Profile


    @DELETE(Constant.GET_USER + "/{userId}")
    suspend fun deleteUser(
        @Path("userId") userId: String,
        @Header("Authorization") authorization: String,
    ): Delete

    @GET(Constant.MY_PETS)
    suspend fun getMyPets(
        @Header("Authorization") authorization: String,
    ): Home
}