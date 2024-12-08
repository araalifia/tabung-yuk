package com.example.sweproject

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesHelper {
    private const val PREFS_NAME = "savings_prefs"
    private const val TRANSACTIONS_KEY = "transactions"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveTransactions(context: Context, transactions: List<Transaction>) {
        val gson = Gson()
        val json = gson.toJson(transactions)
        getPrefs(context).edit().putString(TRANSACTIONS_KEY, json).apply()
    }

    fun getTransactions(context: Context): List<Transaction> {
        val gson = Gson()
        val json = getPrefs(context).getString(TRANSACTIONS_KEY, null)
        return if (json != null) {
            val type = object : TypeToken<List<Transaction>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
}
