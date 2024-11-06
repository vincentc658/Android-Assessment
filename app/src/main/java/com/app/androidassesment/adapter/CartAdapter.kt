package com.app.androidassesment.adapter

import android.graphics.drawable.Drawable
import com.app.androidassesment.data.CartItem
import com.app.androidassesment.databinding.ItemCartBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class CartAdapter(
    private val cartItems: List<CartItem>,
    private val onQuantityChange: (CartItem, Int) -> Unit,
    private val onDeleteItem: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            binding.productTitle.text = item.title
            binding.productPrice.text = "$${item.price * item.quantity}"
            binding.itemQuantity.text = item.quantity.toString()
            Glide.with(binding.productImage.context)
                .load(item.image)
                .apply(RequestOptions().transform(RoundedCorners(20)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.productImage)
            binding.increaseButton.setOnClickListener {
                onQuantityChange(item, item.quantity + 1)
            }

            binding.decreaseButton.setOnClickListener {
                if (item.quantity > 1) {
                    onQuantityChange(item, item.quantity - 1)
                }
            }

            binding.deleteButton.setOnClickListener {
                onDeleteItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount() = cartItems.size
}
