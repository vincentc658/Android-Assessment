package com.app.androidassesment.network

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.androidassesment.data.CartItem

@Dao
interface CartDao {
    @Query("SELECT * FROM CartItem")
    suspend fun getCartItems(): List<CartItem>

    @Query("SELECT * FROM CartItem WHERE productId = :id LIMIT 1")
    suspend fun getCartItemById(id: Int): CartItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItem)

    @Update
    suspend fun updateCartItem(item: CartItem)

    @Delete
    suspend fun deleteCartItem(item: CartItem)

    @Query("DELETE FROM CartItem")
    suspend fun clearCart()
}