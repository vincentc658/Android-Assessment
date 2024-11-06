package com.app.androidassesment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.androidassesment.data.Review
import com.app.androidassesment.databinding.ItemReviewBinding

class ReviewAdapter(private val reviews: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(private val binding : ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            binding.reviewerName.text = review.name
            binding.reviewText.text = review.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }
    override fun getItemCount(): Int = reviews.size
}
