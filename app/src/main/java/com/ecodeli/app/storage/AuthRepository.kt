package com.ecodeli.app.storage

import com.ecodeli.app.model.LoginRequest
import com.ecodeli.app.model.LoginResponse
import com.ecodeli.app.network.ApiService
import com.ecodeli.app.network.RetrofitInstance
import retrofit2.Response

class AuthRepository {

    private val apiService: ApiService = RetrofitInstance.api

    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return apiService.login(request)
    }
}
