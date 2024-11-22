package com.example.mysrib_cursor.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysrib_cursor.R
import com.example.mysrib_cursor.databinding.ItemCardBinding
import com.example.mysrib_cursor.databinding.ItemHorizontalListBinding

class CardAdapter(private val items: List<CardItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_CARD = 0
        private const val TYPE_HORIZONTAL_LIST = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == items.indexOfFirst { it.title == "IT Tips" }) TYPE_HORIZONTAL_LIST else TYPE_CARD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_CARD) {
            val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CardViewHolder(binding)
        } else {
            val binding = ItemHorizontalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HorizontalListViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CardViewHolder) {
            val item = items[position]
            holder.binding.cardTitle.text = item.title
            holder.binding.cardSubtitle.text = item.subtitle
            holder.binding.root.setBackgroundResource(item.background)

            Glide.with(holder.binding.cardImage.context)
                .load(item.imageResId)
                .placeholder(R.drawable.ic_profile)
                .into(holder.binding.cardImage)

            holder.binding.root.setOnClickListener {
                if (item.title == "Daily Declaration") {
                    it.findNavController().navigate(R.id.nav_daily_declaration)
                } else {
                    Toast.makeText(it.context, "${item.title} under construction", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (holder is HorizontalListViewHolder) {
            if (holder.binding.horizontalRecyclerView.adapter == null) {
                val horizontalItems = listOf(            CardItem("IT Tips", "Multi-GPU with Multi\nNode in SPACE platform", R.drawable.gradient_it_guide, R.drawable.ic_it_guide),
                    CardItem("IT Tips", "Xbox Game Box", R.drawable.gradient_it_guide, R.drawable.ic_it_guide),
                    CardItem("IT Tips", "Sharing directories.", R.drawable.gradient_it_guide, R.drawable.ic_it_guide)
                )
                val horizontalAdapter = ITTipsAdapter(horizontalItems)
                holder.binding.horizontalRecyclerView.layoutManager = LinearLayoutManager(holder.binding.root.context, LinearLayoutManager.HORIZONTAL, false)
                holder.binding.horizontalRecyclerView.adapter = horizontalAdapter
            }
        }
    }

    override fun getItemCount() = items.size

    class CardViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)
    class HorizontalListViewHolder(val binding: ItemHorizontalListBinding) : RecyclerView.ViewHolder(binding.root)
}

data class CardItem(val title: String, val subtitle: String, val background: Int, val imageResId: Int) 