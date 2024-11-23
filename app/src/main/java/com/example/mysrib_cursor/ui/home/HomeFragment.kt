package com.example.mysrib_cursor.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysrib_cursor.R
import com.example.mysrib_cursor.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DailyDeclarationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val items = listOf(
            CardItem("Holidays", "", R.drawable.gradient_bus, R.drawable.gradient_bus),
            CardItem("News", "", R.drawable.gradient_bus, R.drawable.gradient_bus),
            CardItem("Daily Declaration", "", R.drawable.gradient_daily_declarations, R.drawable.ic_declaration),
            CardItem("Hybrid Scheduler", "Non Compliance :0\nPending: 0", R.drawable.gradient_hybrid_scheduler, R.drawable.ic_hybrid),
            CardItem("Leave Details", "1 Unresolved\nAvailable: 67\nAvailed: 8", R.drawable.gradient_leave_info, R.drawable.ic_leave),
            CardItem("Life@SRIB", "SRIB latest news,\nevents & other activities", R.drawable.gradient_life_at_srib, R.drawable.ic_lifesrib),
            CardItem("Meal Info", "Rs. 6090.75\nRefilled On: 31 Oct\nLast Spend: Rs. 105\n@SRI-B-JJ-TEA", R.drawable.gradient_meal_info, R.drawable.ic_meal),
            CardItem("Talk to Amber", "Our Employee Engagement\nBOT to build a better\nwork environment", R.drawable.gradient_talk_to_amber, R.drawable.ic_amber),
            CardItem("Health", "Doctor schedule,\ninsurance, network\nhospitals, Employee\nAssistance Program", R.drawable.gradient_health_info, R.drawable.ic_health),
            CardItem("Cab Booking", "Book Adhoc, Late Night\nCab (8pm to 8am) on\nworking days &\nWeekend/Holiday on\nnon-working days.", R.drawable.gradient_cab, R.drawable.ic_cab),
            CardItem("Not Registered", "View the available bus routes\nby searching for your area.", R.drawable.gradient_bus, R.drawable.ic_bus),
            CardItem("IT Tips", "Multi-GPU with Multi\nNode in SPACE platform", R.drawable.gradient_it_guide, R.drawable.ic_it_guide)
        )

        if (viewModel.workStatus.isNotEmpty()){
            items.first { it.title == "Daily Declaration" }.apply {
                subtitle = "${viewModel.submitDate} \n ${viewModel.submitTime}"
                background = R.drawable.gradient_cab
                imageResId = R.drawable.wfo_icon
            }
        }

        // Updated Holiday list for 2024
        val holidays = listOf(
            Holiday("Republic Day", "26 Jan", "Friday"),
            Holiday("Holi", "25 Mar", "Monday"),
            Holiday("Good Friday", "29 Mar", "Friday"),
            Holiday("Independence Day", "15 Aug", "Thursday"),
            Holiday("Gandhi Jayanti", "02 Oct", "Wednesday"),
            Holiday("Dussehra", "11 Oct", "Friday"),
            Holiday("Diwali", "31 Oct", "Thursday"),
            Holiday("Christmas", "25 Dec", "Wednesday"),
            Holiday("Makar Sankranti", "15 Jan", "Monday"),
            Holiday("Eid al-Fitr", "10 Apr", "Wednesday"),
            Holiday("Raksha Bandhan", "19 Aug", "Monday"),
        )

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CardAdapter(items, holidays)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}