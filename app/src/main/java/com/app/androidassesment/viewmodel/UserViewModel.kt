package com.app.androidassesment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.androidassesment.data.Profile
import com.app.androidassesment.data.User
import com.app.androidassesment.repository.UserRepository
import com.app.androidassesment.util.ApiResult
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _profileResponse = MutableLiveData<Profile>()
    val profileResponse: LiveData<Profile> get() = _profileResponse

    private val _loginResponse: MutableLiveData<String> = MutableLiveData()
    val loginResponse: LiveData<String> = _loginResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getProfile(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getProfile(id)) {
                is ApiResult.Success -> {
                    _isLoading.value = false
                    _profileResponse.postValue(result.data)
                }

                is ApiResult.Failure -> {
                    _isLoading.value = false
                    _error.value=result.message
                }
            }
        }
    }
    fun login(user: User) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.login(user)) {
                is ApiResult.Success -> {
                    _isLoading.value = false
                    _loginResponse.value= result.data.token
                }
                is ApiResult.Failure -> {
                    _isLoading.value = false
                    _error.value=result.message
                }
            }
        }
    }
}
