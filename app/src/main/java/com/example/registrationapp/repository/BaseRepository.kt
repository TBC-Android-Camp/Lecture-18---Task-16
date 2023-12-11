package com.example.registrationapp.repository

import com.example.registrationapp.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseRepository {
    suspend fun <T> apiCall(apiCall: suspend () -> Response<T>): Flow<Result<T>> = flow {
        try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                emit(Result.Success(response.body()!!))
            } else {
                emit(Result.Error(false, response.code(), response.errorBody()))
            }
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    emit(Result.Error(false, e.code(), e.response()?.errorBody()))
                }
                else -> {
                    emit(Result.Error(true, null, null))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}