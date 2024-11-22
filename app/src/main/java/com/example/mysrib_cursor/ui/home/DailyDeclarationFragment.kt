package com.example.mysrib_cursor.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mysrib_cursor.databinding.FragmentDailyDeclarationBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DailyDeclarationFragment : Fragment() {

    private var _binding: FragmentDailyDeclarationBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyDeclarationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().reference

        val submitButton: Button = binding.submitButton
        submitButton.setOnClickListener {
            if (validateForm()) {
                uploadFormData()
            } else {
                Toast.makeText(context, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun uploadFormData() {
        val isInBangalore = binding.radioYesBangalore.isChecked
        val haveAnySymptoms = binding.radioYesSymptoms.isChecked
        val workStatus = when {
            binding.radioWfo.isChecked -> "WFO"
            binding.radioWfh.isChecked -> "WFH"
            binding.radioFullDayLeave.isChecked -> "Full Day Leave/FH"
            binding.radioHalfDayLeave.isChecked -> "Half Day Leave"
            binding.radioBusinessTravel.isChecked -> "Business Travel"
            else -> "Not Specified"
        }

        val formData = mapOf(
            "name" to binding.name.text.toString(),
            "genId" to "15640185",
            "isInBangalore" to isInBangalore,
            "haveAnySymptoms" to haveAnySymptoms,
            "workStatus" to workStatus
            // Add additional fields as needed
        )

        database.child("declarations").push().setValue(formData)
            .addOnSuccessListener {
                Toast.makeText(context, "Form submitted successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to submit form.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun validateForm(): Boolean {
        // Validate name
        if (binding.name.text.toString().trim().isEmpty()) {
            binding.name.error = "Name is required"
            binding.name.requestFocus()
            return false
        }

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
            !binding.radioBusinessTravel.isChecked) {
            Toast.makeText(context, "Please select your work status", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate Nov 25 status
        if (!binding.radioWfo25.isChecked && 
            !binding.radioWfh25.isChecked && 
            !binding.radioLeave25.isChecked && 
            !binding.radioBiz25.isChecked) {
            Toast.makeText(context, "Please select status for Nov 25", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate other days similarly...

        // Validate declaration checkbox
        if (!binding.declarationCheckbox.isChecked) {
            Toast.makeText(context, "Please agree to the declaration", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 