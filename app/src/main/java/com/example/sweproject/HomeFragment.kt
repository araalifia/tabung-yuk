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


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}