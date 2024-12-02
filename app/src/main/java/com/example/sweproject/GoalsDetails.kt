import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sweproject.R

class GoalsDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goals_details, container, false)

        // Add Savings Button
        val addSavingButton: Button = view.findViewById(R.id.addSaving)
        addSavingButton.setOnClickListener {
            showAddSavingsDialog()
        }

        // Take Savings Button
        val takeSavingButton: Button = view.findViewById(R.id.takeSaving)
        takeSavingButton.setOnClickListener {
            showTakeSavingsDialog()
        }

        return view
    }

    private fun showAddSavingsDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_savings, null)

        val dialog = AlertDialog.Builder(context ?: return)
            .setView(dialogView)
            .create()

        val saveButton: Button = dialogView.findViewById(R.id.saveButton)
        val amountInput: EditText = dialogView.findViewById(R.id.amountInput)

        saveButton.setOnClickListener {
            val enteredAmount = amountInput.text.toString()

            if (enteredAmount.isEmpty()) {
                amountInput.error = "Please enter an amount"
            } else {
                try {
                    val amount = enteredAmount.toInt() // Attempt to parse as an integer
                    // Perform action with `amount`
                    Toast.makeText(context, "Amount saved: $amount", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                } catch (e: NumberFormatException) {
                    amountInput.error = "Please enter a valid integer"
                }
            }
        }

        dialog.show()
        dialog.window?.setLayout(800, ViewGroup.LayoutParams.WRAP_CONTENT) // Adjust size if needed
    }

    private fun showTakeSavingsDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_savings, null)

        val dialog = AlertDialog.Builder(context ?: return)
            .setView(dialogView)
            .create()

        val dialogTitle: TextView = dialogView.findViewById(R.id.dialogTitle)
        dialogTitle.text = "Take Savings"

        val saveButton: Button = dialogView.findViewById(R.id.saveButton)
        val amountInput: EditText = dialogView.findViewById(R.id.amountInput)

        saveButton.text = "Withdraw"
        saveButton.setOnClickListener {
            val enteredAmount = amountInput.text.toString()

            if (enteredAmount.isEmpty()) {
                amountInput.error = "Please enter an amount"
            } else {
                try {
                    val amount = enteredAmount.toInt() // Attempt to parse as an integer
                    // Perform action with `amount`
                    Toast.makeText(context, "Amount withdrawn: $amount", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                } catch (e: NumberFormatException) {
                    amountInput.error = "Please enter a valid integer"
                }
            }
        }

        dialog.show()
        dialog.window?.setLayout(800, ViewGroup.LayoutParams.WRAP_CONTENT) // Adjust size if needed
    }
}
