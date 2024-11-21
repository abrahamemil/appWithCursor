package com.example.mysrib_cursor.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysrib_cursor.R
import com.example.mysrib_cursor.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val items = listOf(
            CardItem("Daily Declaration", "", R.drawable.gradient_daily_declarations, R.drawable.ic_profile),
            CardItem("Hybrid Scheduler", "Non Compliance :0\nPending: 0", R.drawable.gradient_hybrid_scheduler, R.drawable.ic_profile),
            CardItem("Leave Details", "1 Unresolved\nAvailable: 67\nAvailed: 8", R.drawable.gradient_daily_declarations, R.drawable.ic_profile),
            CardItem("Life@SRIB", "SRIB latest news,\nevents & other activities", R.drawable.gradient_hybrid_scheduler, R.drawable.ic_profile),
            CardItem("Meal Info", "Rs. 6090.75\nRefilled On: 31 Oct\nLast Spend: Rs. 105\n@SRI-B-JJ-TEA", R.drawable.gradient_daily_declarations, R.drawable.ic_profile),
            CardItem("Talk to Amber", "Our Employee Engagement\nBOT to build a better\nwork environment", R.drawable.gradient_hybrid_scheduler, R.drawable.ic_profile),
            CardItem("Health", "Doctor schedule,\ninsurance, network\nhospitals, Employee\nAssistance Program", R.drawable.gradient_daily_declarations, R.drawable.ic_profile),
            CardItem("Cab Booking", "Book Adhoc, Late Night\nCab (8pm to 8am) on\nworking days &\nWeekend/Holiday on\nnon-working days.", R.drawable.gradient_hybrid_scheduler, R.drawable.ic_profile),
            CardItem("Not Registered", "View the available bus routes\nby searching for your area.", R.drawable.gradient_daily_declarations, R.drawable.ic_profile),
            CardItem("IT Tips", "Multi-GPU with Multi\nNode in SPACE platform", R.drawable.gradient_hybrid_scheduler, R.drawable.ic_profile),
        )

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CardAdapter(items)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}