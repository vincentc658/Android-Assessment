package com.app.androidassesment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.androidassesment.data.Product
import com.app.androidassesment.repository.ProductRepository

import androidx.lifecycle.*
import com.app.androidassesment.util.ApiResult
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() = _categories

    private val _errorProduct = MutableLiveData<String>()
    val errorProduct: LiveData<String> get() = _errorProduct

    private val _errorCategory = MutableLiveData<String>()
    val errorCategory: LiveData<String> get() = _errorCategory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingCategories = MutableLiveData<Boolean>()
    val isLoadingCategories: LiveData<Boolean> = _isLoadingCategories

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product> = _selectedProduct

    init {
        fetchProducts()
        fetchCategories()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.getProducts()) {
                is ApiResult.Success -> {
                    _products.postValue(result.data)
                    _isLoading.value = false
                }

                is ApiResult.Failure -> {
                    _isLoading.value = false
                    _errorProduct.postValue(result.message)
                }
            }
        }
    }

    fun fetchCategories() {
        viewModelScope.launch {
            _isLoadingCategories.value = true
            when (val result = repository.getCategories()) {
                is ApiResult.Success -> {
                    _categories.postValue(result.data)
                    _isLoadingCategories.value = false
                }

                is ApiResult.Failure -> {
                    _isLoadingCategories.value = false
                    _errorCategory.postValue(result.message)
                }
            }
        }
    }

    fun fetchProductsByCategory(category: String) {
        _products.postValue(listOf())
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getProductsByCategory(category)) {
                is ApiResult.Success -> {
                    _products.postValue(result.data)
                    _isLoading.value = false
                }

                is ApiResult.Failure -> {
                    _isLoading.value = false
                    _errorProduct.postValue(result.message)
                }
            }
        }
    }
    fun loadProductDetails(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getProductsById(id)) {
                is ApiResult.Success -> {
                    _selectedProduct.postValue(result.data)
                    _isLoading.value = false
                }

                is ApiResult.Failure -> {
                    _isLoading.value = false
                    _errorProduct.postValue(result.message)
                }
            }
        }
    }
}

