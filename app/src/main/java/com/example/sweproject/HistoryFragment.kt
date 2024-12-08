package com.example.sweproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment

class HistoryFragment : Fragment() {

    private lateinit var transactionListView: ListView
    private lateinit var transactionRepository: TransactionRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        transactionListView = view.findViewById(R.id.historyTransactionListView)
        transactionRepository = TransactionRepository(requireContext())

        // Update the transaction list
        updateTransactionHistory()
    }

    private fun updateTransactionHistory() {
        // Retrieve and sort transactions by date descending
        val transactions = transactionRepository.getAllTransactions().sortedByDescending { it.date }

        if (transactions.isEmpty()) {
            transactionListView.visibility = View.GONE
            view?.findViewById<TextView>(R.id.noHistoryText)?.visibility = View.VISIBLE
        } else {
            transactionListView.visibility = View.VISIBLE
            view?.findViewById<TextView>(R.id.noHistoryText)?.visibility = View.GONE

            // Create formatted transaction details
            val transactionDetails = transactions.map { transaction ->
                val typeLabel = when (transaction.type) {
                    TransactionType.Income -> "Income"
                    TransactionType.Expense -> "Expense"
                }

                "${transaction.date} - [$typeLabel] ${transaction.category}: Rp ${transaction.amount}\n" +
                        "Title: ${transaction.title}\nNote: ${transaction.note}"
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
