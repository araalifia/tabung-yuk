package com.example.sweproject

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts

class AddSavingsFragment : Fragment() {

    private lateinit var dateText1: TextView
    private lateinit var dateText2: TextView
    private lateinit var calendarIcon1: ImageView
    private lateinit var calendarIcon2: ImageView
    private lateinit var imageView: ImageView
    private lateinit var spinner: Spinner
    private var selectedImageUri: Uri? = null

    // New way to handle activity results
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                selectedImageUri = data?.data
                if (selectedImageUri != null) {
                    imageView.setImageURI(selectedImageUri)
                } else {
                    Toast.makeText(requireContext(), "Error selecting image", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_savings, container, false)

        // Initialize the ImageView
        dateText1 = view.findViewById(R.id.editStartDate)
        dateText2 = view.findViewById(R.id.editEndDate)
        calendarIcon1 = view.findViewById(R.id.calendarStart)
        calendarIcon2 = view.findViewById(R.id.calendarEnd)
        imageView = view.findViewById(R.id.uploadImage)

        // Set up the calendar icon click listener
        calendarIcon1.setOnClickListener {
            showDatePickerStart()
        }

        calendarIcon2.setOnClickListener {
            showDatePickerEnd()
        }

        // Set click listener on the ImageView
        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
        }

        // Initialize the Spinner
        spinner = view.findViewById(R.id.Savingscategory)

        // Populate Spinner with categories
        val categories = listOf("Education", "Travel", "Health", "Shopping","Emergency","Investment","Retirement","Home")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        return view
    }

    private fun showDatePickerStart() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "${getMonthName(selectedMonth)} $selectedDay, $selectedYear"
                dateText1.text = formattedDate
            },
            year, month, day
        ).show()
    }

    private fun showDatePickerEnd() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "${getMonthName(selectedMonth)} $selectedDay, $selectedYear"
                dateText2.text = formattedDate
            },
            year, month, day
        ).show()
    }

    private fun getMonthName(month: Int): String {
        val months = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        return months[month]
    }
}
