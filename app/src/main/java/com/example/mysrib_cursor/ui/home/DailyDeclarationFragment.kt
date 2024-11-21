package com.example.mysrib_cursor.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
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
            uploadFormData()
        }

        return root
    }

    private fun uploadFormData() {
        val isInBangalore = binding.radioYesBangalore.isChecked
        val name = binding.name.text
        // Collect other form data as needed

        val formData = mapOf(
            "name" to name,
            "genId" to "15840145",
            "isInBangalore" to isInBangalore
            // Add other form fields
        )

        database.child("declarations").push().setValue(formData)
            .addOnSuccessListener {
                Toast.makeText(context, "Form submitted successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to submit form.", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 