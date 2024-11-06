package com.app.androidassesment.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.androidassesment.R
import com.app.androidassesment.adapter.CartAdapter
import com.app.androidassesment.adapter.OrderSummaryAdapter
import com.app.androidassesment.data.CartItem
import com.app.androidassesment.databinding.ActivityCartBinding
import com.app.androidassesment.databinding.DialogOrderSummaryBinding
import com.app.androidassesment.util.BaseApp
import com.app.androidassesment.viewmodel.CartViewModel
import org.koin.android.ext.android.inject

class CartActivity : BaseApp() {
    private lateinit var binding: ActivityCartBinding
    private val cartViewModel: CartViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Cart"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        setupCartRecyclerView()
        observeCartItems()
        cartViewModel.getCart()
        binding.checkoutButton.setOnClickListener {
            showOrderSummaryDialog()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setupCartRecyclerView() {
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartViewModel.cartItems.observe(this) { cartItems ->
            updateTotalPrice(cartItems)
            binding.cartRecyclerView.adapter = CartAdapter(
                cartItems,
                onQuantityChange = { item, newQuantity ->
                    cartViewModel.updateQuantity(item, newQuantity)
                },
                onDeleteItem = { item ->
                    cartViewModel.deleteCartItem(item)
                }
            )
        }
    }

    private fun updateTotalPrice(cartItems: List<CartItem>) {
        val total = cartItems.sumOf { it.price * it.quantity }
        binding.totalPriceText.text = getString(R.string.total, String.format("%.2f", total))
    }

    private fun observeCartItems() {
        cartViewModel.cartItems.observe(this) { cartItems ->
            updateTotalPrice(cartItems)
            (binding.cartRecyclerView.adapter as CartAdapter).notifyDataSetChanged()
        }
        cartViewModel.message.observe(this) { message ->
            showToast(message)
        }
    }

    private fun showOrderSummaryDialog() {
        val cartItems = cartViewModel.cartItems.value ?: emptyList()
        if (cartItems.isEmpty()) {
            showToast("Your cart is empty!")
            return
        }

        val dialogBinding = DialogOrderSummaryBinding.inflate(LayoutInflater.from(this))
        dialogBinding.orderSummaryRecyclerView.layoutManager = LinearLayoutManager(this)
        dialogBinding.orderSummaryRecyclerView.adapter = OrderSummaryAdapter(cartItems)
        val totalPrice = cartItems.sumOf { it.price * it.quantity }
        dialogBinding.totalPriceText.text = "Total: $${String.format("%.2f", totalPrice)}"

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()

        dialogBinding.confirmButton.setOnClickListener {
            completeCheckout()
            dialog.dismiss()
        }
        dialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
    private fun completeCheckout() {
        cartViewModel.checkout()
        showToast("Thank you for your purchase!")
        finish()
    }
}