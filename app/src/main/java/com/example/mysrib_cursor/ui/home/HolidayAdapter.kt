package com.example.mysrib_cursor.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.mysrib_cursor.R
import com.google.firebase.database.collection.LLRBNode.Color
import java.text.SimpleDateFormat
import java.util.*

data class Holiday(val name: String, val date: String, val day: String)

class HolidayAdapter(private val holidays: List<Holiday>) : RecyclerView.Adapter<HolidayAdapter.HolidayViewHolder>() {

    private val sortedHolidays = holidays.sortedBy { parseDate(it.date) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolidayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_holiday, parent, false)
        return HolidayViewHolder(view)
    }

    override fun onBindViewHolder(holder: HolidayViewHolder, position: Int) {
        val holiday = sortedHolidays[position]
        holder.dateTextView.text = holiday.date
        holder.dayTextView.text = holiday.day[0].toString()

        val holidayDate = parseDate(holiday.date)
        val currentDate = Calendar.getInstance().time

        if (position == sortedHolidays.size - 1){
            holder.holidayItem.background = AppCompatResources.getDrawable(holder.itemView.context, R.drawable.holiday_item_background_selected);
            holder.dateTextView.setTextColor(android.graphics.Color.WHITE)        }
        else{
            holder.dateTextView.setTextColor(android.graphics.Color.parseColor("#737070"))
            holder.holidayItem.background = AppCompatResources.getDrawable(holder.itemView.context, R.drawable.holiday_item_background);
        }

        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, "${holiday.name} (${holiday.date}, ${holiday.day})", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = sortedHolidays.size

    class HolidayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.holidayDate)
        val dayTextView: TextView = itemView.findViewById(R.id.holidayDay)
        val holidayItem: LinearLayout = itemView.findViewById(R.id.holidayItem)
    }

    private fun parseDate(dateString: String): Date {
        val format = SimpleDateFormat("dd MMM", Locale.getDefault())
        return format.parse(dateString) ?: Date()
    }
} 