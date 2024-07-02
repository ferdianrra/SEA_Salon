package com.dicoding.seasalon.ui.history

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.seasalon.R
import com.dicoding.seasalon.data.Order
import com.dicoding.seasalon.databinding.ItemHistoryBinding

class HistoryAdapter (private val listOrder: List<Order>): RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listOrder.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (date, photo, name, time, type) = listOrder[position]
        val context = holder.itemView.context
        val icon = context.resources.obtainTypedArray(R.array.cover_services)

        holder.apply {
            binding.ivIcon.setImageResource(icon.getResourceId(photo.toInt(), -1))
            binding.tvType.text = type
            binding.tvTime.text = time
            binding.tvDate.text = date
            binding.tvName.text = name

        }
    }
}