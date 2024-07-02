package com.dicoding.seasalon.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.seasalon.data.Review
import com.dicoding.seasalon.databinding.ItemReviewBinding

class ListCommentAdapter(private val listReview: List<Review>): RecyclerView.Adapter<ListCommentAdapter.ListViewHolder>() {
    class ListViewHolder(var binding:ItemReviewBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val(name, rate, comment) = listReview[position]
        holder.apply {
            binding.tvComment.text = comment
            binding.tvRating.text = rate
            binding.tvUsername.text = name
        }
    }

    override fun getItemCount(): Int = listReview.size
}