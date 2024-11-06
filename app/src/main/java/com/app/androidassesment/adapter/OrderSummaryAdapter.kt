package com.app.androidassesment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.androidassesment.data.CartItem
import com.app.androidassesment.databinding.ItemOrderSummaryBinding

class OrderSummaryAdapter(private val cartItems: List<CartItem>) : RecyclerView.Adapter<OrderSummaryAdapter.OrderSummaryViewHolder>() {

    inner class OrderSummaryViewHolder(private val binding: ItemOrderSummaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            binding.itemTitle.text = cartItem.title
            binding.itemQuantity.text = "Qty: ${cartItem.quantity}"
            binding.itemSubtotal.text = "$${String.format("%.2f", cartItem.price * cartItem.quantity)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderSummaryViewHolder {
        val binding = ItemOrderSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderSummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderSummaryViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount() = cartItems.size
}
