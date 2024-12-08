package com.example.sweproject

import dGoalsDetails // Ensure this is correctly imported
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class goalsFragment : Fragment() {
    private lateinit var ongoingButton: Button
    private lateinit var achievedButton: Button
    private lateinit var goalsDetails: ImageView
    private lateinit var addGoalsButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val view = inflater.inflate(R.layout.fragment_goals, container, false)

        ongoingButton = view.findViewById(R.id.onGoingButton)
        achievedButton = view.findViewById(R.id.achievedButton)
        goalsDetails = view.findViewById(R.id.goalsDetails)
        addGoalsButton = view.findViewById(R.id.AddGoalsButton)

        // Set click listeners
        ongoingButton.setOnClickListener {
            toggleTransactionType(isOnGoing = true)
        }

        achievedButton.setOnClickListener {
            toggleTransactionType(isOnGoing = false)
        }

        // Click listener for the goalsDetails ImageView
        goalsDetails.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, dGoalsDetails()) // Ensure GoalsDetails is correctly referenced
                .addToBackStack("GoalsDetails")
                .commit()
        }

        // Click listener for the Add Goals button
        addGoalsButton.setOnClickListener {
            val addSavingsFragment = AddSavingsFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, addSavingsFragment)
                .addToBackStack("AddGoals")
                .commit()
        }

        return view
    }

    // Method to receive savings data
    fun addSavings(savings: Savings) {
        // Handle the saved savings data
        Toast.makeText(requireContext(), "Savings Added: ${savings.category}", Toast.LENGTH_SHORT).show()

        // Additional logic can be added here to display the savings or update the UI
    }

    private fun toggleTransactionType(isOnGoing: Boolean) {
        // Your existing toggle logic
    }
}