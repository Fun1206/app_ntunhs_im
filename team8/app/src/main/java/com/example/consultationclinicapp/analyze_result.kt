package com.example.consultationclinicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class analyze_result : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyze_result)

        val home = findViewById<ImageButton>(R.id.home_btn)
        val previous = findViewById<Button>(R.id.previous5_btn)
        val googlemap = findViewById<Button>(R.id.googlemap_btn)

        home.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }
        previous.setOnClickListener {
            var symintent = Intent(this,Symptoms_input::class.java)
            startActivity(symintent)
        }
        googlemap.setOnClickListener {
            var symintent = Intent(this,Symptoms_input::class.java)
            startActivity(symintent)
        }
    }
}