package com.example.registrationapp.network

import com.example.registrationapp.network.model.LoginAndRegisterRequest
import com.example.registrationapp.network.model.LoginResponse
import com.example.registrationapp.network.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("login")
    suspend fun login(@Body body: LoginAndRegisterRequest): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body body: LoginAndRegisterRequest): Response<RegisterResponse>

}