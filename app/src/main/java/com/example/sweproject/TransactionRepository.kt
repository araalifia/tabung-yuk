class TransactionRepository {
    private val transactions = mutableListOf<Transaction>()

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }

    fun getAllTransactions(): List<Transaction> {
        return transactions
    }

    fun getTotalIncome(): Double {
        return transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    }

    fun getTotalExpenses(): Double {
        return transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    }

    fun getTotalBalance(): Double {
        return getTotalIncome() - getTotalExpenses()
    }
}
