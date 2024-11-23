package com.example.mysrib_cursor.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mysrib_cursor.R
import com.example.mysrib_cursor.databinding.FragmentDailyDeclarationBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DailyDeclarationFragment : Fragment() {

    private var _binding: FragmentDailyDeclarationBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private lateinit var dateTextView: TextView
    private val viewModel: DailyDeclarationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyDeclarationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().reference

        dateTextView = binding.dateTextView

        dateTextView.setOnClickListener {
            showDatePicker()
        }

        populateFieldsFromViewModel()

        val submitButton: Button = binding.submitButton
        submitButton.setOnClickListener {
            if (validateForm()) {
                saveDataToViewModel()
                uploadFormData()
            } else {
                Toast.makeText(context, "Please fill in all required fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return root
    }

    private fun populateFieldsFromViewModel() {
        binding.userName.text = viewModel.name
        binding.userGenId.text = viewModel.genId
        if (viewModel.workStatus.isNotEmpty()){
            binding.llDaily.visibility = View.GONE
            binding.llDailyDone.visibility = View.VISIBLE
            binding.radioYesBangalore.isChecked = viewModel.isInBangalore
            binding.radioYesSymptoms.isChecked = viewModel.haveAnySymptoms
            binding.dailyDoneTime.text = viewModel.submitTime
            binding.dailyDoneDate.text = viewModel.submitDate
        }else{
            binding.llDaily.visibility = View.VISIBLE
            binding.llDailyDone.visibility = View.GONE
        }
        when (viewModel.workStatus) {
            "WFO" -> binding.radioWfo.isChecked = true
            "WFH" -> binding.radioWfh.isChecked = true
            "Full Day Leave/FH" -> binding.radioFullDayLeave.isChecked = true
            "Half Day Leave" -> binding.radioHalfDayLeave.isChecked = true
            "Business Travel" -> binding.radioBusinessTravel.isChecked = true
        }

        viewModel.weeklyPlan.forEach { (day, plan) ->
            when (day) {
                "Monday" -> {
                    setDayPlan(binding.rgDay1, plan)
                }

                "Tuesday" -> setDayPlan(binding.rgDay2, plan)
                "Wednesday" -> setDayPlan(binding.rgDay3, plan)
                "Thursday" -> setDayPlan(binding.rgDay4, plan)
                "Friday" -> setDayPlan(binding.rgDay5, plan)
            }
        }
    }

    private fun setDayPlan(radioGroup: RadioGroup, plan: String) {
        when (plan) {
            "WFO" -> {
                when (radioGroup.id) {
                    R.id.rg_day1 -> radioGroup.check(R.id.rb_wfo_day1)
                    R.id.rg_day2 -> radioGroup.check(R.id.rb_wfo_day2)
                    R.id.rg_day3 -> radioGroup.check(R.id.rb_wfo_day3)
                    R.id.rg_day4 -> radioGroup.check(R.id.rb_wfo_day4)
                    R.id.rg_day5 -> radioGroup.check(R.id.rb_wfo_day5)
                }
            }
        "WFH" -> {
            when (radioGroup.id) {
                R.id.rg_day1 -> radioGroup.check(R.id.rb_wfo_day1)
                R.id.rg_day2 -> radioGroup.check(R.id.rb_wfh_day2)
                R.id.rg_day3 -> radioGroup.check(R.id.rb_wfh_day3)
                R.id.rg_day4 -> radioGroup.check(R.id.rb_wfh_day4)
                R.id.rg_day5 -> radioGroup.check(R.id.rb_wfh_day5)
            }
        }
        "Leave/FH" -> {
            when (radioGroup.id) {
                R.id.rg_day1 -> radioGroup.check(R.id.rb_leave_day1)
                R.id.rg_day2 -> radioGroup.check(R.id.rb_leave_day2)
                R.id.rg_day3 -> radioGroup.check(R.id.rb_leave_day3)
                R.id.rg_day4 -> radioGroup.check(R.id.rb_leave_day4)
                R.id.rg_day5 -> radioGroup.check(R.id.rb_leave_day5)
            }
        }
        "Business Travel" -> {
            when (radioGroup.id) {
                R.id.rg_day1 -> radioGroup.check(R.id.rb_biz_day1)
                R.id.rg_day2 -> radioGroup.check(R.id.rb_biz_day2)
                R.id.rg_day3 -> radioGroup.check(R.id.rb_biz_day3)
                R.id.rg_day4 -> radioGroup.check(R.id.rb_biz_day4)
                R.id.rg_day5 -> radioGroup.check(R.id.rb_biz_day5)
            }
        }
    }
}

private fun showDatePicker() {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog =
        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            dateTextView.text = selectedDate
        }, year, month, day)

    datePickerDialog.datePicker.maxDate = calendar.timeInMillis
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    datePickerDialog.datePicker.minDate = calendar.timeInMillis

    datePickerDialog.show()
}

private fun saveDataToViewModel() {
    viewModel.name = binding.userName.text.toString()
    viewModel.genId = "123456"
    viewModel.isInBangalore = binding.radioYesBangalore.isChecked
    viewModel.haveAnySymptoms = binding.radioYesSymptoms.isChecked
    viewModel.workStatus = when {
        binding.radioWfo.isChecked -> "WFO"
        binding.radioWfh.isChecked -> "WFH"
        binding.radioFullDayLeave.isChecked -> "Full Day Leave/FH"
        binding.radioHalfDayLeave.isChecked -> "Half Day Leave"
        binding.radioBusinessTravel.isChecked -> "Business Travel"
        else -> "Not Specified"
    }
    viewModel.weeklyPlan = mapOf(
        "Monday" to getDayPlan(binding.rgDay1),
        "Tuesday" to getDayPlan(binding.rgDay2),
        "Wednesday" to getDayPlan(binding.rgDay3),
        "Thursday" to getDayPlan(binding.rgDay4),
        "Friday" to getDayPlan(binding.rgDay5)
    )
    val sdf = SimpleDateFormat("hh:mm")
    val currentDate = sdf.format(Date())
    viewModel.submitTime = currentDate
    viewModel.submitDate = binding.dateTextView.text.toString()
}

private fun uploadFormData() {
    val formData = mapOf(
        "name" to viewModel.name,
        "genId" to viewModel.genId,
        "isInBangalore" to viewModel.isInBangalore,
        "haveAnySymptoms" to viewModel.haveAnySymptoms,
        "workStatus" to viewModel.workStatus,
        "weeklyPlan" to viewModel.weeklyPlan
    )

    database.child("declarations").push().setValue(formData)
        .addOnSuccessListener {
            Toast.makeText(requireContext(), "Form submitted successfully!", Toast.LENGTH_SHORT)
                .show()
//            binding.llDaily.setBackgroundColor(resources.getColor(R.color.samsung_blue))
            populateFieldsFromViewModel()
            binding.root.smoothScrollTo(0,0)
        }
        .addOnFailureListener {
            Toast.makeText(requireContext(), "Failed to submit form.", Toast.LENGTH_SHORT).show()
        }
}

private fun getDayPlan(radioGroup: RadioGroup): String {
    return when (radioGroup.checkedRadioButtonId) {
        R.id.rb_wfo_day1, R.id.rb_wfo_day2, R.id.rb_wfo_day3, R.id.rb_wfo_day4, R.id.rb_wfo_day5 -> "WFO"
        R.id.rb_wfh_day1, R.id.rb_wfh_day2, R.id.rb_wfh_day3, R.id.rb_wfh_day4, R.id.rb_wfh_day5 -> "WFH"
        R.id.rb_leave_day1, R.id.rb_leave_day2, R.id.rb_leave_day3, R.id.rb_leave_day4, R.id.rb_leave_day5 -> "Leave/FH"
        R.id.rb_biz_day1, R.id.rb_biz_day2, R.id.rb_biz_day3, R.id.rb_biz_day4, R.id.rb_biz_day5 -> "Business Travel"
        else -> "Not Specified"
    }
}

private fun validateForm(): Boolean {
    // Validate Bangalore status
    if (!binding.radioYesBangalore.isChecked && !binding.radioNoBangalore.isChecked) {
        Toast.makeText(context, "Please select if you are in Bangalore", Toast.LENGTH_SHORT).show()
        return false
    }

    // Validate symptoms status
    if (!binding.radioYesSymptoms.isChecked && !binding.radioNoSymptoms.isChecked) {
        Toast.makeText(context, "Please select if you have any symptoms", Toast.LENGTH_SHORT).show()
        return false
    }

    // Validate work status
    if (!binding.radioWfo.isChecked &&
        !binding.radioWfh.isChecked &&
        !binding.radioFullDayLeave.isChecked &&
        !binding.radioHalfDayLeave.isChecked &&
        !binding.radioBusinessTravel.isChecked
    ) {
        Toast.makeText(context, "Please select your work status", Toast.LENGTH_SHORT).show()
        return false
    }

    // Validate contact number
    if (binding.contactNo.text.isNullOrEmpty()) {
        Toast.makeText(context, "Please enter your contact number", Toast.LENGTH_SHORT).show()
        return false
    }

    // Validate emergency contact number
    if (binding.emergencyContact.text.isNullOrEmpty()) {
        Toast.makeText(context, "Please enter your emergency contact number", Toast.LENGTH_SHORT)
            .show()
        return false
    }

    // Validate weekly plan
    if (!isWeeklyPlanValid()) {
        Toast.makeText(context, "Please complete your weekly plan", Toast.LENGTH_SHORT).show()
        return false
    }

    // Validate declaration checkbox
    if (!binding.declarationCheckbox.isChecked) {
        Toast.makeText(context, "Please agree to the declaration", Toast.LENGTH_SHORT).show()
        return false
    }

    return true
}

private fun isWeeklyPlanValid(): Boolean {
    // Check if at least one radio button is selected for each day
    return binding.rgDay1.checkedRadioButtonId != -1 &&
            binding.rgDay2.checkedRadioButtonId != -1 &&
            binding.rgDay3.checkedRadioButtonId != -1 &&
            binding.rgDay4.checkedRadioButtonId != -1 &&
            binding.rgDay5.checkedRadioButtonId != -1
}

override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}
} 