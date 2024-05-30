package com.example.consultationclinicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val analyze_btn = findViewById<Button>(R.id.analyze_btn)
        val research_btn = findViewById<Button>(R.id.research_btn)


        analyze_btn.setOnClickListener {
            var analyzeInputintent = Intent(this,analyze_input::class.java)
            startActivity(analyzeInputintent)
        }
    }

}