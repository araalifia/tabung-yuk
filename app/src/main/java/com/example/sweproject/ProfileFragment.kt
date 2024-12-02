package com.example.sweproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class ProfileFragment : Fragment() {

    private lateinit var imageView: ImageView
    private var selectedImageUri: Uri? = null

    // New way to handle activity results
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                selectedImageUri = data?.data
                if (selectedImageUri != null) {
                    imageView.setImageURI(selectedImageUri)
                } else {
                    Toast.makeText(requireContext(), "Error selecting image", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize the ImageView
        imageView = view.findViewById(R.id.uploadProfile)

        // Set click listener on the ImageView
        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editProfile: TextView = view.findViewById(R.id.editProfile)
        editProfile.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, edit_profile())
                .addToBackStack("EditProfile") // Preserve navigation stack
                .commit()
        }

        val editLanguage: TextView = view.findViewById(R.id.editLanguage)
        editLanguage.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, edit_language())
                .addToBackStack("EditLanguage") // Preserve navigation stack
                .commit()
        }

        val editCurrency: TextView = view.findViewById(R.id.editCurrency)
        editCurrency.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, edit_currency())
                .addToBackStack("EditCurrency") // Preserve navigation stack
                .commit()
        }
    }

}
