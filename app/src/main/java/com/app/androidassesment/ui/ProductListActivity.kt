package com.app.androidassesment.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.androidassesment.R
import com.app.androidassesment.adapter.CategoryAdapter
import com.app.androidassesment.adapter.ProductAdapter
import com.app.androidassesment.databinding.ActivityProductListBinding
import com.app.androidassesment.databinding.BottomSheetProfileBinding
import com.app.androidassesment.databinding.DialogOrderSummaryBinding
import com.app.androidassesment.util.BaseApp
import com.app.androidassesment.viewmodel.ProductViewModel
import com.app.androidassesment.viewmodel.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.android.ext.android.inject

class ProductListActivity : BaseApp() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityProductListBinding
    private val viewModel: ProductViewModel by inject()
    private val userViewModel: UserViewModel by inject()
    private lateinit var bottomSheetDialog :BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomSheetDialog = BottomSheetDialog(this)
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.cartIcon.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        binding.profileIcon.setOnClickListener {
            userViewModel.getProfile(1)
        }
        observeData()
        viewModel.fetchProducts()
        viewModel.fetchCategories()
    }

    private fun showErrorProduct(message: String?) {
        binding.productsErrorTextView.apply {
            text = message ?: "An error occurred"
            showView(true)
        }
    }

    private fun showErrorCategory(message: String?) {
        binding.categoriesErrorTextView.apply {
            text = message ?: "An error occurred"
            showView(true)
        }
    }

    private fun observeData() {
        viewModel.categories.observe(this) { resource ->
            val categories = resource ?: emptyList()
            binding.categoriesRecyclerView.adapter = CategoryAdapter(categories) { category ->
                viewModel.fetchProductsByCategory(category)
            }
        }
        viewModel.products.observe(this) { resource ->
            val products = resource ?: emptyList()
            binding.productsRecyclerView.adapter = ProductAdapter(products) { productId ->
                val intent = ProductDetailActivity.newIntent(this, productId)
                startActivity(intent)
            }
        }
        viewModel.isLoading.observe(this) { isShow ->
            binding.productsLoadingIndicator.showView(isShow)
        }
        viewModel.isLoadingCategories.observe(this) { isShow ->
            binding.categoriesLoadingIndicator.showView(isShow)
        }
        viewModel.errorProduct.observe(this) { message ->
            showErrorProduct(message)
        }
        viewModel.errorCategory.observe(this) { message ->
            showErrorCategory(message)
        }
        userViewModel.isLoading.observe(this) { isLoading ->
            binding.loadingIndicator.showView(isLoading)
        }

        userViewModel.profileResponse.observe(this) { userProfile ->
            userProfile?.let {
                val dialogBinding = BottomSheetProfileBinding.inflate(LayoutInflater.from(this))
                dialogBinding.nameTextView.text =
                    getString(
                        R.string.fullname,
                        userProfile.name.firstname,
                        userProfile.name.lastname
                    )
                dialogBinding.usernameTextView.text =
                    getString(R.string.username, userProfile.username)
                dialogBinding.emailTextView.text = getString(R.string.email, userProfile.email)
                dialogBinding.phoneTextView.text =
                    getString(R.string.phonenumber, userProfile.phone)
                dialogBinding.addressTextView.text =
                    getString(
                        R.string.address,
                        userProfile.address.street,
                        userProfile.address.city,
                        userProfile.address.zipcode
                    )
                sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                dialogBinding.logoutButton.setOnClickListener {
                    sharedPreferences.edit().remove("isLoggedIn").apply()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                bottomSheetDialog.setContentView(dialogBinding.root)
                bottomSheetDialog.show()
            } ?: run {
                showToast("Failed to load profile")
            }
        }
    }
}