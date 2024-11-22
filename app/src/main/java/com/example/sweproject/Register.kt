package com.example.sweproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sweproject.databinding.ActivityRegisterBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child( "users")

        binding.signupButton.setOnClickListener {
            val signupUsername = binding.signupUsername.text.toString()
            val signupPassword = binding.signupPassword.text.toString()

            if (signupUsername.isNotEmpty() && signupPassword.isNotEmpty()){
                if (signupPassword.length >= 7 && signupUsername.length >= 7 && signupUsername.length <= 20) {
                    signUpUser(signupUsername, signupPassword)
                } else if(signupPassword.length < 7 ) {
                    Toast.makeText(this@Register, "Password must be at least 7 characters", Toast.LENGTH_SHORT).show()
                } else if (signupUsername.length > 20){
                    Toast.makeText(this@Register, "Username must be less than 20 characters", Toast.LENGTH_SHORT).show()
                } else if (signupUsername.length < 7){
                    Toast.makeText(this@Register, "Username must be at least 7 characters", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this@Register, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
        }
        binding.loginRedirect.setOnClickListener {
            startActivity(Intent(this@Register, Login::class.java))
            finish()
        }

    }

    private fun signUpUser(username: String, password: String) {
        databaseReference.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(datasnapshot: DataSnapshot) {
                    if (!datasnapshot.exists()){
                        val id = databaseReference.push().key
                        val userData = UserData(id, username, password)
                        databaseReference.child(id!!).setValue(userData)
                        Toast.makeText(this@Register, "Signup Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@Register, Login::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@Register, "User already exists", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@Register, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                }
            })

    }
}