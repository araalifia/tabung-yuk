package com.example.sweproject

import java.util.Date

data class Transaction(
    val id: Int,
    val type: TransactionType,
    val amount: Double,
    val category: String,
    val date: Date,
    val note: String,
    val title: String
)
