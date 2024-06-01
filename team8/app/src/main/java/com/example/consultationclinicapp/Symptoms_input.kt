package com.example.consultationclinicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class Symptoms_input : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptoms_input)

        val home = findViewById<ImageButton>(R.id.home_btn)
        val pervious = findViewById<Button>(R.id.previous4_btn)
        val next = findViewById<Button>(R.id.next4_btn)
        val log = findViewById<Button>(R.id.log_btn)/*查看已選擇症狀*/

        home.setOnClickListener {
            var homeintent = Intent(this,MainActivity::class.java)
            startActivity(homeintent)
        }
        pervious.setOnClickListener {
            var subintent = Intent(this,SubParts_input::class.java)
            startActivity(subintent)
        }
        next.setOnClickListener {
            var resultintent = Intent(this,analyze_result::class.java)
            startActivity(resultintent)
        }
    }
}