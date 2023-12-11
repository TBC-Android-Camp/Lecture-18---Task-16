package com.example.registrationapp.network

import okhttp3.ResponseBody


sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data object Loading : Result<Nothing>()
    data class Error(
        val isNetworkError: Boolean?,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : Result<Nothing>()
}