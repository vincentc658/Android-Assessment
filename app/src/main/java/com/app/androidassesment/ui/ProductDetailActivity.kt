package com.app.androidassesment.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.androidassesment.adapter.ReviewAdapter
import com.app.androidassesment.data.Review
import com.app.androidassesment.databinding.ActivityProductDetailBinding
import com.app.androidassesment.util.BaseApp
import com.app.androidassesment.viewmodel.CartViewModel
import com.app.androidassesment.viewmodel.ProductViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import org.koin.android.ext.android.inject

class ProductDetailActivity : BaseApp() {
    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductViewModel by inject()
    private val cartModel: CartViewModel by inject()
    companion object {
        const val ID_PRODUCT = "ID_PRODUCT"
        fun newIntent(
            context: Context,
            id: Int,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(ID_PRODUCT, id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Product Details"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        binding.reviewsRecyclerView.layoutManager =
            LinearLayoutManager(this)
        binding.reviewsRecyclerView.adapter= ReviewAdapter(listOf(Review(),Review(),Review(),Review()))

        binding.addToCartButton.setOnClickListener {
            viewModel.selectedProduct.value?.let {
                cartModel.addToCart(it)
            }
        }
        val id= intent.getIntExtra(ID_PRODUCT, 0)
        viewModel.loadProductDetails(id)
        obServeData()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    private fun obServeData(){

        viewModel.selectedProduct.observe(this) { product ->
            binding.productTitle.text = product.title
            binding.productPrice.text = "$${product.price}"
            binding.productDescription.text = "$${product.description}"
            Glide.with(binding.productImage.context)
                .load(product.image)
                .apply(RequestOptions().transform(RoundedCorners(20)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.imageLoadingIndicator.showView(false)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.imageLoadingIndicator.showView(false)
                        return false
                    }
                })
                .into(binding.productImage)
        }
        cartModel.message.observe(this) { message ->
            showToast(message)
        }
    }
}