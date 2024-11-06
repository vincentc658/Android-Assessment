package com.app.androidassesment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.androidassesment.data.CartItem
import com.app.androidassesment.data.Product
import com.app.androidassesment.repository.CartRepository
import com.app.androidassesment.util.ApiResult
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    init {
        viewModelScope.launch {
            repository.getCartItems()
        }
    }

    fun getCart() {
        viewModelScope.launch {
            when (val result = repository.getCartItems()) {
                is ApiResult.Success -> {
                    _cartItems.postValue(result.data)
                }

                is ApiResult.Failure -> {
                    _message.postValue(result.message)
                }
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            when (val result = repository.addToCart(product)) {
                is ApiResult.Success -> {
                    _message.postValue("Added to Cart")
                }

                is ApiResult.Failure -> {
                    _message.postValue(result.message)
                }
            }
        }
    }

    fun updateCartItem(item: CartItem) {
        viewModelScope.launch {
            when (val result = repository.updateCartItem(item)) {
                is ApiResult.Success -> {
                    // Handle success (e.g., update UI or show a success message)
                }

                is ApiResult.Failure -> {
                    _message.postValue(result.message)
                }
            }
        }
    }

    fun deleteCartItem(item: CartItem) {
        viewModelScope.launch {
            when (val result = repository.deleteCartItem(item)) {
                is ApiResult.Success -> {
                    getCart()
                }
                is ApiResult.Failure -> {
                    _message.postValue(result.message)
                }
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            when (val result = repository.clearCart()) {
                is ApiResult.Success -> {
                }

                is ApiResult.Failure -> {
                    _message.postValue(result.message)
                }
            }
        }
    }

    fun updateQuantity(item: CartItem, quantity: Int) {
        val updatedItem = item.copy(quantity = quantity)
        viewModelScope.launch {
            when (val result = repository.updateCartItem(updatedItem)) {
                is ApiResult.Success -> {
                    getCart()
                }
                is ApiResult.Failure -> {
                    _message.postValue(result.message)
                }
            }
        }
    }
    fun checkout() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}