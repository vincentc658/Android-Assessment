package com.app.androidassesment.network

import com.app.androidassesment.data.Product
import com.app.androidassesment.data.Profile
import com.app.androidassesment.data.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body user: User): User

    @GET("users/{id}")
    suspend fun getProfile(@Path("id") id: Int): Profile

    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<Product>

    @GET("products/{id}")
    suspend fun getProductDetails(@Path("id") id: Int): Product
}