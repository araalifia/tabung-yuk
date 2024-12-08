import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlin.collections.mutableMapOf
import androidx.fragment.app.Fragment
import com.example.sweproject.R
import com.example.sweproject.Transaction
import com.example.sweproject.TransactionRepository
import com.example.sweproject.TransactionType
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.util.*


class AnalysisFragment : Fragment() {
    private lateinit var dateText: TextView
    private lateinit var calendarStart: ImageView
    private lateinit var before: ImageView
    private lateinit var after: ImageView
    private lateinit var barChart: BarChart
    private var currentCalendar: Calendar = Calendar.getInstance()
    private lateinit var transactionRepository: TransactionRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_analysis, container, false)

        dateText = view.findViewById(R.id.dateText)
        calendarStart = view.findViewById(R.id.calendarStart)
        before = view.findViewById(R.id.before)
        after = view.findViewById(R.id.after)
        barChart = view.findViewById(R.id.barChart)

        transactionRepository = TransactionRepository(requireContext())

        // Initialize the date display with the current month and year
        updateDateDisplay()
        updateBarChart()

        // Set an OnClickListener for the calendar icon
        calendarStart.setOnClickListener {
            openMonthYearPicker()
        }

        // Set OnClickListener for the back arrow
        before.setOnClickListener {
            moveToPreviousMonth()
        }

        // Set OnClickListener for the forward arrow
        after.setOnClickListener {
            moveToNextMonth()
        }

        return view
    }

    private fun openMonthYearPicker() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, _ ->
                calendar.set(year, month, 1)
                currentCalendar = calendar
                updateDateDisplay() // Update the TextView after selecting a date
                updateBarChart() // Update the bar chart after selecting a date
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Set to spinner mode to hide the day view
        datePickerDialog.datePicker.findViewById<View>(
            resources.getIdentifier("day", "id", "android")
        )?.visibility = View.GONE

        datePickerDialog.show()
    }

    // Update the date display TextView with the current month and year
    private fun updateDateDisplay() {
        val monthName = currentCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())!!
        val year = currentCalendar.get(Calendar.YEAR)
        dateText.text = "$monthName $year"
    }

    // Move to the previous month
    private fun moveToPreviousMonth() {
        currentCalendar.add(Calendar.MONTH, -1) // Decrease the month by 1
        updateDateDisplay() // Update the TextView to reflect the new month
    }

    // Move to the next month
    private fun moveToNextMonth() {
        currentCalendar.add(Calendar.MONTH, 1) // Increase the month by 1
        updateDateDisplay() // Update the TextView to reflect the new month
        updateBarChart() // Update the chart data
    }

    private fun updateBarChart() {
        // Fetch transactions for the selected month
        val transactions = transactionRepository.getAllTransactions().filter {
            val calendar = Calendar.getInstance()
            calendar.time = it.date // Assuming it.date is a Date object
            calendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH) &&
                    calendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)
        }

        // Group transactions by type and category, then calculate totals
        val incomeMap = transactions.filter { it.type == TransactionType.Income }
            .groupBy { it.category }
            .mapValues { (_, list) -> list.sumOf { it.amount } }

        val expenseMap = transactions.filter { it.type == TransactionType.Expense }
            .groupBy { it.category }
            .mapValues { (_, list) -> list.sumOf { it.amount } }

        // Create entries for the bar chart
        val incomeEntries = incomeMap.entries.mapIndexed { index, entry ->
            BarEntry(index.toFloat(), entry.value.toFloat())
        }

        val expenseEntries = expenseMap.entries.mapIndexed { index, entry ->
            BarEntry(index.toFloat(), entry.value.toFloat())
        }

        // Create datasets and combine them into a BarData object
        val incomeDataSet = BarDataSet(incomeEntries, "Income").apply {
            color = resources.getColor(R.color.incomeBlue, null)
        }
        val expenseDataSet = BarDataSet(expenseEntries, "Expense").apply {
            color = resources.getColor(R.color.expenseRed, null)
        }

        val barData = BarData(incomeDataSet, expenseDataSet)
        barChart.data = barData

        // Customize the X-axis
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setLabelCount(incomeEntries.size + expenseEntries.size, false)

        // Refresh the chart
        barChart.invalidate()
    }

}