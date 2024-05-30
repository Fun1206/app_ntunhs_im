package com.example.consultationclinicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class frontbody_female : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frontbody_female)
        val home = findViewById<ImageButton>(R.id.home_btn)
        val frontf_btn = findViewById<Button>(R.id.frontf_btn)
        val backf_btn = findViewById<Button>(R.id.backf_btn)
        val pervious = findViewById<Button>(R.id.previous_btn)
        val next = findViewById<Button>(R.id.next2_btn)

        home.setOnClickListener {
            var homeintent = Intent(this,MainActivity::class.java)
            startActivity(homeintent)
        }

        /*frontf_btn.setOnClickListener {
            var frontfemaleintent = Intent(this,frontbody_female::class.java)
            startActivity(frontfemaleintent)
        }*/

        backf_btn.setOnClickListener {
            var backfemaleintent = Intent(this,backbody_female::class.java)
            startActivity(backfemaleintent)
        }

        pervious.setOnClickListener {
            var inputintent = Intent(this,analyze_input::class.java)
            startActivity(inputintent)
        }
        next.setOnClickListener {
            var inputsymintent = Intent(this,symptom_input::class.java)
            inputsymintent.putExtra("pervious_record", "female")
            startActivity(inputsymintent)
        }
    }
}