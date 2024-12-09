import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sweproject.R
import com.example.sweproject.Transaction
import com.example.sweproject.TransactionAdapter
import com.example.sweproject.TransactionRepository
import com.example.sweproject.TransactionType
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class AnalysisFragment : Fragment() {

    private lateinit var barChart: BarChart
    private lateinit var transactionRecyclerView: RecyclerView
    private lateinit var transactionRepository: TransactionRepository
    private var transactionList = arrayListOf<Transaction>()

    private lateinit var tvVisibilityNoData: TextView
    private lateinit var noDataImage: ImageView
    private lateinit var tvNoDataTitle: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize BarChart
        barChart = view.findViewById(R.id.barChart)

        // Initialize RecyclerView
        transactionRecyclerView = view.findViewById(R.id.rvTransaction)
        transactionRecyclerView.layoutManager = LinearLayoutManager(this.activity)
        transactionRecyclerView.setHasFixedSize(true)

        // Initialize other views
        tvVisibilityNoData = view.findViewById(R.id.tvVisibilityNoData)
        noDataImage = view.findViewById(R.id.noDataImage)
        tvNoDataTitle = view.findViewById(R.id.tvNoDataTitle)

        // Initialize TransactionRepository
        transactionRepository = TransactionRepository(requireContext())

        // Fetch and process transaction data
        getTransactionData()
    }

    private fun getTransactionData() {
        tvVisibilityNoData.visibility = View.GONE
        transactionRecyclerView.visibility = View.GONE
        noDataImage.visibility = View.GONE
        tvNoDataTitle.visibility = View.GONE

        val transactions = transactionRepository.getAllTransactions().sortedByDescending { it.date }

        if (transactions.isEmpty()) {
            noDataImage.visibility = View.VISIBLE
            tvNoDataTitle.visibility = View.VISIBLE
            tvVisibilityNoData.visibility = View.VISIBLE
            tvVisibilityNoData.text = "No transactions found"
        } else {
            transactionList.clear()

            var incomeAmount = 0.0
            var expenseAmount = 0.0

            for (transaction in transactions) {
                when (transaction.type) {
                    TransactionType.Expense -> expenseAmount += transaction.amount
                    TransactionType.Income -> incomeAmount += transaction.amount
                }
                transactionList.add(transaction)
            }

            updateBarChart(incomeAmount, expenseAmount)

            val mAdapter = TransactionAdapter(transactionList)
            transactionRecyclerView.adapter = mAdapter
            transactionRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun updateBarChart(incomeAmount: Double, expenseAmount: Double) {
        val barEntries = ArrayList<BarEntry>()
        barEntries.add(BarEntry(1f, incomeAmount.toFloat()))
        barEntries.add(BarEntry(2f, expenseAmount.toFloat()))

        val dataSet = BarDataSet(barEntries, "Income vs Expenses")
        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        val barData = BarData(dataSet)
        barData.barWidth = 0.5f

        barChart.data = barData
        barChart.invalidate()

        barChart.description.isEnabled = false
        barChart.legend.isEnabled = true
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisRight.isEnabled = false
        barChart.xAxis.isEnabled = true
    }
}
