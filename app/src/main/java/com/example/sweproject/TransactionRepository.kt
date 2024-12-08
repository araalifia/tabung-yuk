package com.example.sweproject

import android.content.Context

class TransactionRepository(private val context: Context) {
    private var transactions = SharedPreferencesHelper.getTransactions(context).toMutableList()

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
        SharedPreferencesHelper.saveTransactions(context, transactions)
    }

    fun getAllTransactions(): List<Transaction> {
        return transactions
    }

    fun getTotalIncome(): Double {
        return transactions.filter { it.type == TransactionType.Income }.sumOf { it.amount }
    }

    fun getTotalExpenses(): Double {
        return transactions.filter { it.type == TransactionType.Expense }.sumOf { it.amount }
    }

    fun getTotalBalance(): Double {
        return getTotalIncome() - getTotalExpenses()
    }
}
