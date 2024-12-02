import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.sweproject.R
import java.util.*


class AnalysisFragment : Fragment() {
    private lateinit var dateText: TextView
    private lateinit var calendarStart: ImageView
    private lateinit var before: ImageView
    private lateinit var after: ImageView
    private var currentCalendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_analysis, container, false)

        dateText = view.findViewById(R.id.dateText)
        calendarStart = view.findViewById(R.id.calendarStart)
        before = view.findViewById(R.id.before)
        after = view.findViewById(R.id.after)

        // Initialize the date display with the current month and year
        updateDateDisplay()

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
    }
}
