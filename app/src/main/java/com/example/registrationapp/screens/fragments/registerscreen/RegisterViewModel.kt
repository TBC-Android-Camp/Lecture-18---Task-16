package com.example.registrationapp.screens.fragments.registerscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrationapp.network.Result
import com.example.registrationapp.network.RetrofitInstance
import com.example.registrationapp.network.model.LoginAndRegisterRequest
import com.example.registrationapp.network.model.RegisterResponse
import com.example.registrationapp.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository = AuthRepository(RetrofitInstance.api)) : ViewModel() {
    private val _registerState = MutableStateFlow<Result<RegisterResponse>>(Result.Loading)
    val registerState = _registerState

    fun register(email: String, password: String) {
        val registerRequest = LoginAndRegisterRequest(email = email, password = password)
        viewModelScope.launch {
            repository.register(registerRequest).collectLatest { _registerState.value = it }
        }
    }
}