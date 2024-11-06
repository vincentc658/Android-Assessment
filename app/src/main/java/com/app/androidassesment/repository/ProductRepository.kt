package com.app.androidassesment.repository

import com.app.androidassesment.data.Product
import com.app.androidassesment.network.ApiService
import com.app.androidassesment.util.ApiResult

class ProductRepository(private val apiService: ApiService) {

    suspend fun getProducts(): ApiResult<List<Product>> {
        return try {
            val response = apiService.getProducts()
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Failure("Failed to load products: ${e.message}")
        }
    }

    suspend fun getCategories(): ApiResult<List<String>> {
        return try {
            val response = apiService.getCategories()
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Failure("Failed to load categories: ${e.message}")
        }
    }

    suspend fun getProductsByCategory(category: String): ApiResult<List<Product>> {
        return try {
            val response = apiService.getProductsByCategory(category)
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Failure("Failed to load products for category '$category': ${e.message}")
        }
    }
    suspend fun getProductsById(id: Int): ApiResult<Product> {
        return try {
            val response = apiService.getProductDetails(id)
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Failure("Failed to load products for category : ${e.message}")
        }
    }
}
