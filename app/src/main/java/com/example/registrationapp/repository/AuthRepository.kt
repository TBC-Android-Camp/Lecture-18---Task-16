package com.example.registrationapp.repository

import com.example.registrationapp.network.Api
import com.example.registrationapp.network.model.LoginAndRegisterRequest

class AuthRepository(private val api: Api) : BaseRepository() {

    suspend fun login(requestBody: LoginAndRegisterRequest) =
        apiCall { api.login(requestBody) }

    suspend fun register(requestBody: LoginAndRegisterRequest) =
        apiCall { api.register(requestBody) }

}