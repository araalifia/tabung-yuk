package com.example.sweproject

import GoalsDetails
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [goalsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class goalsFragment : Fragment() {
    private lateinit var ongoingButton: Button
    private lateinit var achievedButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val view = inflater.inflate(R.layout.fragment_goals, container, false)

        ongoingButton = view.findViewById(R.id.onGoingButton)
        achievedButton = view.findViewById(R.id.achievedButton)

        // Set up button click listeners
        ongoingButton.setOnClickListener {
            toggleTransactionType(isOnGoing = true)
        }
        achievedButton.setOnClickListener {
            toggleTransactionType(isOnGoing = false)
        }

        return view
    }

    private fun toggleTransactionType(isOnGoing: Boolean) {
        if (isOnGoing) {
            // Change Income button to blue background
            ongoingButton.setBackgroundColor(resources.getColor(R.color.incomeBlue, null))
            ongoingButton.setTextColor(resources.getColor(android.R.color.white, null))

            // Reset Expense button to default background
            achievedButton.setBackgroundColor(resources.getColor(android.R.color.white, null))
            achievedButton.setTextColor(resources.getColor(android.R.color.black, null))

        } else {
            // Change Expense button to red background
            achievedButton.setBackgroundColor(resources.getColor(R.color.expenseRed, null))
            achievedButton.setTextColor(resources.getColor(android.R.color.white, null))

            // Reset Income button to default background
            ongoingButton.setBackgroundColor(resources.getColor(android.R.color.white, null))
            ongoingButton.setTextColor(resources.getColor(android.R.color.black, null))

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val AddGoalsButton: Button = view.findViewById(R.id.AddGoalsButton)
        AddGoalsButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddSavingsFragment())
                .addToBackStack("AddGoals") // Preserve navigation stack
                .commit()
        }

        val goalsDetails: ImageView = view.findViewById(R.id.goalsDetails)
        goalsDetails.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, GoalsDetails())
                .addToBackStack("GoalsDetails") // Preserve navigation stack
                .commit()
        }
    }
}