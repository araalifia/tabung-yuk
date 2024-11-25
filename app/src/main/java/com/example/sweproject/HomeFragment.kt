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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    // Variables for saldo logic
    private lateinit var totalSaldo: TextView
    private lateinit var eyeIcon: ImageView
    private var isSaldoVisible = true
    private var saldoAmount ="Rp3,560,724"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize views
        totalSaldo= view.findViewById(R.id.totalSaldo)
        eyeIcon = view.findViewById(R.id.eyeIcon)

        // Set initial saldo amount
        totalSaldo.text = saldoAmount

        // Set up click listener for the eye icon
        eyeIcon.setOnClickListener {
            toggleSaldoVisibility()
        }


        return view
    }

    // Method to toggle visibility of "Total Saldo"
    private fun toggleSaldoVisibility() {
        if (isSaldoVisible) {
            totalSaldo.text = "********" // Hide saldo
            eyeIcon.setImageResource(R.drawable.eye_closed_icon) // Use closed-eye icon
        } else {
            totalSaldo.text = saldoAmount // Show saldo
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