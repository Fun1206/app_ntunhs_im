package com.example.consultationclinicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class search_drugs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_drugs)

        val home = findViewById<ImageButton>(R.id.home_btn)

        home.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }
    }
}