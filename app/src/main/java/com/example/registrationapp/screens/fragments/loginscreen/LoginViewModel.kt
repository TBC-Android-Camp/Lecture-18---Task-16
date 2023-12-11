package com.example.registrationapp.screens.fragments.loginscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrationapp.network.Result
import com.example.registrationapp.network.RetrofitInstance
import com.example.registrationapp.network.model.LoginAndRegisterRequest
import com.example.registrationapp.network.model.LoginResponse
import com.example.registrationapp.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: AuthRepository = AuthRepository(RetrofitInstance.api)) : ViewModel() {
    private val _loginState = MutableStateFlow<Result<LoginResponse>>(Result.Loading)
    val loginState = _loginState

    fun login(email: String, password: String) {
        val loginRequest = LoginAndRegisterRequest(email = email, password = password)
        viewModelScope.launch {
            repository.login(loginRequest).collect { _loginState.value = it }
        }
    }

}