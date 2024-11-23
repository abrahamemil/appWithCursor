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
import com.example.mysrib_cursor.databinding.ItemHolidayCardBinding

class CardAdapter(private val items: List<CardItem>, private val holidays: List<Holiday>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_CARD = 0
        private const val TYPE_HOLIDAY = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == items.indexOfFirst { it.title == "Holidays" }) TYPE_HOLIDAY else TYPE_CARD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_CARD) {
            val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CardViewHolder(binding)
        } else {
            val binding = ItemHolidayCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HolidayViewHolder(binding)
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
        } else if (holder is HolidayViewHolder) {
            holder.binding.holidayRecyclerView.layoutManager = LinearLayoutManager(holder.binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            holder.binding.holidayRecyclerView.adapter = HolidayAdapter(holidays)
        }
    }

    override fun getItemCount() = items.size

    class CardViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)
    class HolidayViewHolder(val binding: ItemHolidayCardBinding) : RecyclerView.ViewHolder(binding.root)
}

data class CardItem(val title: String, val subtitle: String, val background: Int, val imageResId: Int) 