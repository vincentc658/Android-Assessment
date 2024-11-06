package com.app.androidassesment.repository

import com.app.androidassesment.data.Profile
import com.app.androidassesment.data.User
import com.app.androidassesment.network.ApiService
import com.app.androidassesment.util.ApiResult

class UserRepository(private val apiService: ApiService) {
    suspend fun login(user: User): ApiResult<User> {
        return try {
            val response = apiService.login(user)
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Failure("Failed to load products: ${e.message}")
        }
    }
    suspend fun getProfile(id: Int): ApiResult<Profile> {
        return try {
            val response = apiService.getProfile(id)
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Failure("Failed to load products: ${e.message}")
        }
    }
}