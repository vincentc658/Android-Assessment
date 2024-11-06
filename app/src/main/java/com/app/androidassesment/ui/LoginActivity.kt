package com.app.androidassesment.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.app.androidassesment.data.User
import com.app.androidassesment.databinding.ActivityLoginBinding
import com.app.androidassesment.util.BaseApp
import com.app.androidassesment.viewmodel.UserViewModel
import org.koin.android.ext.android.inject

class LoginActivity : BaseApp() {
    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by inject()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            startActivity(Intent(this, ProductListActivity::class.java))
            finish()
            return
        }

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            userViewModel.login(User(username, password))
        }

        userViewModel.loginResponse.observe(this) { _s ->
            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
            startActivity(Intent(this, ProductListActivity::class.java))
            finish()
        }
        userViewModel.isLoading.observe(this) { isLoading ->
            binding.loadingIndicator.showView(isLoading)
        }
    }
}
