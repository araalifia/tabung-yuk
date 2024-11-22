package com.example.sweproject

import android.content.Intent
import android.view.View
import android.os.Bundle
import android.os.Message
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.callbackFlow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

    }
    fun MoveLayout(view: View?) {
        val intent = Intent(
            this@MainActivity,
            Login::class.java
        )
        startActivity(intent)
    }
    fun Register1 (view: View?) {
        val intent = Intent(
            this@MainActivity,
            Register::class.java
        )
        startActivity(intent)
    }

}

