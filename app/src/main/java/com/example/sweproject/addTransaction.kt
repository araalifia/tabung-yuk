package com.example.sweproject

import android.app.DatePickerDialog
import java.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import java.util.Date

class addTransaction : Fragment() {
    private lateinit var dateText: TextView
    private lateinit var calendarIcon: ImageView
    private lateinit var categorySpinner: Spinner
    private lateinit var incomeButton: Button
    private lateinit var expenseButton: Button
    private lateinit var saveButton: Button
    private var transactionType = TransactionType.Income
    private lateinit var transactionRepository: TransactionRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val view = inflater.inflate(R.layout.fragment_add_transaction, container, false)

        transactionRepository = TransactionRepository(requireContext())

        // Initialize views
        dateText = view.findViewById(R.id.dateText)
        calendarIcon = view.findViewById(R.id.calendarIcon)
        categorySpinner = view.findViewById(R.id.categorySpinner)
        incomeButton = view.findViewById(R.id.incomeButton)
        expenseButton = view.findViewById(R.id.expenseButton)
        saveButton = view.findViewById(R.id.SaveButton)

        // Set up the spinner with default Income categories
        setupCategorySpinner(R.array.income_categories)

        // Set up the calendar icon click listener
        calendarIcon.setOnClickListener {
            showDatePickerDialog()
        }

        // Set up button click listeners
        incomeButton.setOnClickListener {
            toggleTransactionType(isIncome = true)
        }
        expenseButton.setOnClickListener {
            toggleTransactionType(isIncome = false)
        }
        saveButton.setOnClickListener {
            saveTransaction()
        }

        return view
    }

    private fun setupCategorySpinner(categoryArrayRes: Int) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            categoryArrayRes,
            android.R.layout.simple_spinner_item
        )

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the Spinner
        categorySpinner.adapter = adapter
    }

    private fun toggleTransactionType(isIncome: Boolean) {
        if (isIncome) {
            // Change Income button to blue background
            incomeButton.setBackgroundColor(resources.getColor(R.color.incomeBlue, null))
            incomeButton.setTextColor(resources.getColor(android.R.color.white, null))

            // Reset Expense button to default background
            expenseButton.setBackgroundColor(resources.getColor(android.R.color.white, null))
            expenseButton.setTextColor(resources.getColor(android.R.color.black, null))

            // Update the spinner options for Income
            setupCategorySpinner(R.array.income_categories)
            TransactionType.Income
        } else {
            // Change Expense button to red background
            expenseButton.setBackgroundColor(resources.getColor(R.color.expenseRed, null))
            expenseButton.setTextColor(resources.getColor(android.R.color.white, null))

            // Reset Income button to default background
            incomeButton.setBackgroundColor(resources.getColor(android.R.color.white, null))
            incomeButton.setTextColor(resources.getColor(android.R.color.black, null))

            // Update the spinner options for Expense
            setupCategorySpinner(R.array.expense_categories)
            transactionType= TransactionType.Expense
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "${getMonthName(selectedMonth)} $selectedDay, $selectedYear"
                dateText.text = formattedDate
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
    private fun saveTransaction() {
        val amount = view?.findViewById<EditText>(R.id.editTextBox)?.text.toString().toDoubleOrNull() ?: 0.0
        val title = view?.findViewById<EditText>(R.id.editTitle)?.text.toString()  // Corrected from 'category'
        val category = categorySpinner.selectedItem.toString()  // Use Spinner for category
        val date = dateText.text.toString()
        val note = view?.findViewById<EditText>(R.id.editNotes)?.text.toString()

        if (amount > 0 && title.isNotEmpty()) {
            // Corrected parameter order for the Transaction data class
            val transaction = Transaction(
                id = 0,  // Assuming auto-generated
                type = transactionType,
                amount = amount,
                title = title,    // Corrected
                category = category,  // Use selected spinner value
                date = Calendar.getInstance().time,
                note = note
            )
            transactionRepository.addTransaction(transaction)

            // Navigate back to the home page
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment())
                .commit()
        } else {
            Toast.makeText(requireContext(), "Amount and title must be filled", Toast.LENGTH_SHORT).show()
        }
    }
}
