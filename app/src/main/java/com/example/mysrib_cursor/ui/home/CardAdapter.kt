package com.example.mysrib_cursor.ui.home

import android.os.Handler
import android.os.Looper
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
import com.example.mysrib_cursor.databinding.ItemNewsCardBinding

class CardAdapter(private val items: List<CardItem>, private val holidays: List<Holiday>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_CARD = 0
        private const val TYPE_HOLIDAY = 1
        private const val TYPE_NEWS = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            items.indexOfFirst { it.title == "Holidays" } -> TYPE_HOLIDAY
            items.indexOfFirst { it.title == "News" } -> TYPE_NEWS
            else -> TYPE_CARD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CARD -> {
                val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CardViewHolder(binding)
            }
            TYPE_HOLIDAY -> {
                val binding = ItemHolidayCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HolidayViewHolder(binding)
            }
            TYPE_NEWS -> {
                val binding = ItemNewsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NewsViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CardViewHolder -> {
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
            }
            is HolidayViewHolder -> {
                holder.binding.holidayRecyclerView.layoutManager = LinearLayoutManager(holder.binding.root.context, LinearLayoutManager.HORIZONTAL, false)
                holder.binding.holidayRecyclerView.adapter = HolidayAdapter(holidays)
            }
            is NewsViewHolder -> {
                val newsText = holder.binding.newsText
                val fullText = newsText.text.toString()
                newsText.text = ""

                val handler = Handler(Looper.getMainLooper())
                var index = 0

                val runnable = object : Runnable {
                    override fun run() {
                        if (index < fullText.length) {
                            newsText.append(fullText[index].toString())
                            index++
                            handler.postDelayed(this, 50) // Adjust speed as needed
                        }
                    }
                }
                handler.post(runnable)
            }
        }
    }

    override fun getItemCount() = items.size

    class CardViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)
    class HolidayViewHolder(val binding: ItemHolidayCardBinding) : RecyclerView.ViewHolder(binding.root)
    class NewsViewHolder(val binding: ItemNewsCardBinding) : RecyclerView.ViewHolder(binding.root)
}

data class CardItem(val title: String, val subtitle: String, val background: Int, val imageResId: Int) 