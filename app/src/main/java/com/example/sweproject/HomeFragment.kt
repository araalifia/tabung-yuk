package com.example.sweproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    // Variables for saldo logic
    private lateinit var totalSaldo: TextView
    private lateinit var totalIncomeText: TextView
    private lateinit var totalExpensesText: TextView
    private lateinit var transactionListView: ListView
    private lateinit var eyeIcon: ImageView

    private var isSaldoVisible = true
    private var saldoAmount = "Rp0"
    private lateinit var transactionRepository: TransactionRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        transactionRepository = TransactionRepository(requireContext())

        // Initialize views
        totalSaldo = view.findViewById(R.id.totalSaldo)
        totalIncomeText = view.findViewById(R.id.textView12)
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

    override fun onResume() {
        super.onResume()
        updateSaldo()
        updateTransactionList()
    }

    private fun updateSaldo() {
        val totalIncome = transactionRepository.getTotalIncome()
        val totalExpenses = transactionRepository.getTotalExpenses()
        val totalBalance = transactionRepository.getTotalBalance()

        totalSaldo.text = "${totalBalance}"
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

        val historyRedirect: TextView = view.findViewById(R.id.historyRedirect)
        historyRedirect.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HistoryFragment())
                .addToBackStack("HistoryFragment") // Preserve navigation stack
                .commit()
        }

        updateSaldo()
        updateTransactionList()
    }

    private fun updateTransactionList() {
        // Get the last 5 transactions
        val transactions = transactionRepository.getAllTransactions().takeLast(3)

        if (transactions.isEmpty()) {
            transactionListView.visibility = View.GONE
            view?.findViewById<ImageView>(R.id.noHistoryIcon)?.visibility = View.VISIBLE
        } else {
            transactionListView.visibility = View.VISIBLE
            view?.findViewById<ImageView>(R.id.noHistoryIcon)?.visibility = View.GONE

            // Extract only title and amount
            val transactionDetails = transactions.map { transaction ->
                "${transaction.title}: Rp ${transaction.amount}"
            }

            // Set the adapter
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                transactionDetails
            )
            transactionListView.adapter = adapter
        }
    }


}
