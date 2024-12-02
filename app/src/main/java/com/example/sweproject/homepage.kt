package com.example.sweproject

import AnalysisFragment
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation


class homepage : AppCompatActivity() {
    private var selectedTab = 1
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_homepage)

        val homeLayout: LinearLayout = findViewById(R.id.homelayout)
        val analysisLayout: LinearLayout = findViewById(R.id.analysislayout)
        val goalsLayout: LinearLayout = findViewById(R.id.goalslayout)
        val profileLayout: LinearLayout = findViewById(R.id.profilelayout)

        val homeImage: ImageView = findViewById(R.id.homeimage)
        val analysisImage: ImageView = findViewById(R.id.analysisimage)
        val goalsimage: ImageView = findViewById(R.id.goalsimage)
        val profileImage: ImageView = findViewById(R.id.profileimage)

        val hometxt: TextView = findViewById(R.id.hometxt)
        val analysistxt: TextView = findViewById(R.id.analysistxt)
        val goalstxt: TextView = findViewById(R.id.goalstxt)
        val profiletxt: TextView = findViewById(R.id.profiletxt)

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainer, HomeFragment::class.java, null)
            .commit()

        homeLayout.setOnClickListener{
            if (selectedTab != 1) {
                goalstxt.visibility = View.GONE
                analysistxt.visibility = View.GONE
                profiletxt.visibility = View.GONE

                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, HomeFragment::class.java, null)
                    .commit()

                analysisImage.setImageResource(R.drawable.history_icon)
                goalsimage.setImageResource(R.drawable.goals_icon)
                profileImage.setImageResource(R.drawable.profile_icon)

                analysisLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                goalsLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                profileLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))

                hometxt.visibility = View.VISIBLE
                homeImage.setImageResource(R.drawable.home_selected_icon)
                homeLayout.setBackgroundResource(R.drawable.round_back_home_100)

                val scaleAnimation = ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f)
                scaleAnimation.duration = 200
                scaleAnimation.fillAfter = true
                homeLayout.startAnimation(scaleAnimation)

                selectedTab = 1
            }
        }

        analysisLayout.setOnClickListener {
            if (selectedTab != 2) {
                hometxt.visibility = View.GONE
                goalstxt.visibility = View.GONE
                profiletxt.visibility = View.GONE

                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, AnalysisFragment::class.java, null)
                    .commit()

                homeImage.setImageResource(R.drawable.home_icon)
                goalsimage.setImageResource(R.drawable.goals_icon)
                profileImage.setImageResource(R.drawable.profile_icon)

                homeLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                goalsLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                profileLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))

                analysistxt.visibility = View.VISIBLE
                analysisImage.setImageResource(R.drawable.history_selected_icon)
                analysisLayout.setBackgroundResource(R.drawable.round_back_history_100)

                val scaleAnimation = ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f)
                scaleAnimation.duration = 200
                scaleAnimation.fillAfter = true
                analysisLayout.startAnimation(scaleAnimation)

                selectedTab = 2
            }
        }


        goalsLayout.setOnClickListener {
            if (selectedTab != 3) {
                hometxt.visibility = View.GONE
                analysistxt.visibility = View.GONE
                profiletxt.visibility = View.GONE

                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, goalsFragment::class.java, null)
                    .commit()

                homeImage.setImageResource(R.drawable.home_icon)
                analysisImage.setImageResource(R.drawable.history_icon)
                profileImage.setImageResource(R.drawable.profile_icon)

                homeLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                analysisLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                profileLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))

                goalstxt.visibility = View.VISIBLE
                goalsimage.setImageResource(R.drawable.goals_selected_icon)
                goalsLayout.setBackgroundResource(R.drawable.round_back_goals_100)

                val scaleAnimation = ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f)
                scaleAnimation.duration = 200
                scaleAnimation.fillAfter = true
                goalsLayout.startAnimation(scaleAnimation)

                selectedTab = 3
            }
        }

        profileLayout.setOnClickListener {
            if (selectedTab != 4) {
                hometxt.visibility = View.GONE
                analysistxt.visibility = View.GONE
                goalstxt.visibility = View.GONE

                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, ProfileFragment::class.java, null)
                    .commit()

                homeImage.setImageResource(R.drawable.home_icon)
                analysisImage.setImageResource(R.drawable.history_icon)
                goalsimage.setImageResource(R.drawable.goals_icon)

                homeLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                goalsLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                analysisLayout.setBackgroundColor(resources.getColor(android.R.color.transparent))

                profiletxt.visibility = View.VISIBLE
                profileImage.setImageResource(R.drawable.profile_selected_icon)
                profileLayout.setBackgroundResource(R.drawable.round_back_profile_100)

                val scaleAnimation = ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f)
                scaleAnimation.duration = 200
                scaleAnimation.fillAfter = true
                profileLayout.startAnimation(scaleAnimation)

                selectedTab = 4
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}