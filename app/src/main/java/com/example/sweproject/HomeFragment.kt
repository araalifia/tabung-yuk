package com.example.sweproject

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.commit
import com.example.sweproject.R

class HomeFragment : Fragment() {

    // Variables for saldo logic
    private lateinit var totalSaldo: TextView
    private lateinit var totalIncomeText: TextView 
    private lateinit var totalExpensesText: TextView
    private lateinit var transactionListView: ListView
    private lateinit var eyeIcon: ImageView

    private var isSaldoVisible = true
    private var saldoAmount ="Rp0"
    private var transactionRepository = TransactionRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize views
        totalSaldo= view.findViewById(R.id.totalSaldo)
        totalIncomeText = view.findViewById(R.id.textView12) // Assuming this is for total income
        totalExpensesText = view.findViewById(R.id.textView13)
        transactionListView = view.findViewById(R.id.transactionListView)
        eyeIcon = view.findViewById(R.id.eyeIcon)

        // Set initial saldo amount
       updateSaldo()

        // Set up click listener for the eye icon
        eyeIcon.setOnClickListener {
            toggleSaldoVisibility()
        }

        updateTransactionList()

        return view
    }

    private fun updateSaldo() { 
        val totalIncome = transactionRepository.getTotalIncome() 
        val totalExpenses = transactionRepository.getTotalExpenses() 
        val totalBalance = transactionRepository.getTotalBalance() 
        
        totalSaldo.text = "Total Balance: Rp ${totalBalance}" 
        totalIncomeText.text = "Rp ${totalIncome}" 
        totalExpensesText.text = "Rp ${totalExpenses}"
    }

    // Method to toggle visibility of "Total Saldo"
    private fun toggleSaldoVisibility() {
        if (isSaldoVisible) {
            totalSaldo.text = "********" // Hide saldo
            eyeIcon.setImageResource(R.drawable.eye_closed_icon) // Use closed-eye icon
        } else {
            updateSaldo() // Show saldo
            eyeIcon.setImageResource(R.drawable.eye_icon) // Use open-eye icon
        }
        isSaldoVisible = !isSaldoVisible // Toggle the state
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transactionButton: Button = view.findViewById(R.id.transactionRedirect)
        transactionButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, addTransaction())
                .addToBackStack("AddTransaction") // Preserve navigation stack
                .commit()
        }

        // Find the TextView by ID
        val historyRedirect: TextView = view.findViewById(R.id.historyRedirect)

        // Set the click listener
        historyRedirect.setOnClickListener {
            // Perform fragment transaction
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HistoryFragment()) // Replace with the HistoryFragment
                .addToBackStack("HistoryFragment") // Add to back stack for proper navigation
                .commit()
        }

        updateSaldo()
        updateTransactionList()
    }

    private fun updateTransactionList() { 
        val transactions = transactionRepository.getAllTransactions().takeLast(5)
        val transactionDetails = transactions.map { transaction -> 
            "${transaction.type} ${transaction.category}: Rp ${transaction.amount}\n" + 
                "Title: ${transaction.title}\nDate: ${transaction.date}\nNote: ${transaction.note}" 
            }   
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, transactionDetails) 
            transactionListView.adapter = adapter 
        }


}