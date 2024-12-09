package com.example.sweproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class goalsFragment : Fragment() {

    private lateinit var goalsDetails: ImageView
    private lateinit var addGoalsButton: Button
    private lateinit var savingsRecyclerView: RecyclerView
    private lateinit var savingsAdapter: SavingsAdapter
    private val savingsList: MutableList<Savings> = mutableListOf() // Store the added savings

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val view = inflater.inflate(R.layout.fragment_goals, container, false)

        goalsDetails = view.findViewById(R.id.goalsDetails)
        addGoalsButton = view.findViewById(R.id.AddGoalsButton)
        savingsRecyclerView = view.findViewById(R.id.savingsRecyclerView)

        // Initialize the adapter and RecyclerView
        savingsAdapter = SavingsAdapter(savingsList)
        savingsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        savingsRecyclerView.adapter = savingsAdapter

        // Click listener for the goalsDetails ImageView
        goalsDetails.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, GoalsDetails()) // Ensure GoalsDetails is correctly referenced
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

    // Method to receive and store savings data
    fun addSavings(savings: Savings) {
        // Add the savings to the list
        savingsList.add(savings)
        savingsAdapter.notifyItemInserted(savingsList.size - 1) // Notify adapter about new data

        // Optionally display a toast or update other parts of the UI
        Toast.makeText(requireContext(), "Savings Added: ${savings.category}", Toast.LENGTH_SHORT).show()
    }
}

class SavingsAdapter(private val savingsList: MutableList<Savings>) :
    RecyclerView.Adapter<SavingsAdapter.SavingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_savings, parent, false)
        return SavingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavingsViewHolder, position: Int) {
        val savings = savingsList[position]
        holder.categoryTextView.text = savings.category
        holder.startDateTextView.text = savings.startDate
        holder.endDateTextView.text = savings.endDate
        savings.imageUri?.let {
            holder.imageView.setImageURI(it)
        }
    }

    override fun getItemCount(): Int = savingsList.size

    class SavingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryTextView: TextView = view.findViewById(R.id.savingsCategory)
        val startDateTextView: TextView = view.findViewById(R.id.savingsStartDate)
        val endDateTextView: TextView = view.findViewById(R.id.savingsEndDate)
        val imageView: ImageView = view.findViewById(R.id.savingsImage)
    }
}
