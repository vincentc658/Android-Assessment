package com.app.androidassesment.repository

import com.app.androidassesment.data.CartItem
import com.app.androidassesment.data.Product
import com.app.androidassesment.network.CartDao
import com.app.androidassesment.util.ApiResult

class CartRepository(private val cartDao: CartDao) {

    suspend fun getCartItems(): ApiResult<List<CartItem>>  {
        return try {
            val items = cartDao.getCartItems()
            ApiResult.Success(items)
        } catch (e: Exception) {
            ApiResult.Failure("Failed to load cart items: ${e.message}")
        }
    }

    suspend fun addToCart(product: Product): ApiResult<Unit> {
        return try {
            val existingItem = cartDao.getCartItemById(product.id)
            if (existingItem != null) {
                val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
                cartDao.updateCartItem(updatedItem)
            } else {
                val newItem = CartItem(
                    productId = product.id,
                    title = product.title,
                    price = product.price,
                    image = product.image,
                    quantity = 1
                )
                cartDao.insertCartItem(newItem)
            }
            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Failure("Failed to add item to cart: ${e.message}")
        }
    }

    suspend fun updateCartItem(item: CartItem): ApiResult<Unit> {
        return try {
            cartDao.updateCartItem(item)
            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Failure("Failed to update cart item: ${e.message}")
        }
    }

    suspend fun deleteCartItem(item: CartItem): ApiResult<Unit> {
        return try {
            cartDao.deleteCartItem(item)
            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Failure("Failed to delete cart item: ${e.message}")
        }
    }

    suspend fun clearCart(): ApiResult<Unit>  {
        return try {
            cartDao.clearCart()
            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Failure("Failed to clear cart: ${e.message}")
        }
    }
}
