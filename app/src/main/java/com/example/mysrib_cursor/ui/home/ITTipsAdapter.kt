package com.example.mysrib_cursor.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysrib_cursor.R
import com.example.mysrib_cursor.databinding.ItemCardBinding

class ITTipsAdapter(private val items: List<CardItem>) : RecyclerView.Adapter<ITTipsAdapter.ITTipsViewHolder>() {

    class ITTipsViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ITTipsViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ITTipsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ITTipsViewHolder, position: Int) {
        val item = items[position]
        holder.binding.cardTitle.text = item.title
        holder.binding.cardSubtitle.text = item.subtitle
        holder.binding.root.setBackgroundResource(item.background)

        Glide.with(holder.binding.cardImage.context)
            .load(item.imageResId)
            .placeholder(R.drawable.ic_profile)
            .into(holder.binding.cardImage)
    }

    override fun getItemCount() = items.size
} 