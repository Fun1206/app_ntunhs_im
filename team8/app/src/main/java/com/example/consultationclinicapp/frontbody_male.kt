package com.example.consultationclinicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class frontbody_male : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frontbody_male)

        val home = findViewById<ImageButton>(R.id.home_btn)
        val front_btn = findViewById<Button>(R.id.front_btn)
        val back_btn = findViewById<Button>(R.id.back_btn)
        val pervious = findViewById<Button>(R.id.previous_btn)
        val next = findViewById<Button>(R.id.next2_btn)

        home.setOnClickListener {
            var homeintent = Intent(this,MainActivity::class.java)
            startActivity(homeintent)
        }

        /*front_btn.setOnClickListener {
            var frontmaleintent = Intent(this,frontbody_male::class.java)
            startActivity(frontmaleintent)
        }*/

        back_btn.setOnClickListener {
            var backmaleintent = Intent(this,backbody_male::class.java)
            startActivity(backmaleintent)
        }

        pervious.setOnClickListener {
            var inputintent = Intent(this,analyze_input::class.java)
            startActivity(inputintent)
        }
        next.setOnClickListener {
            var inputsymintent = Intent(this,symptom_input::class.java)
            inputsymintent.putExtra("pervious_record", "male")
            startActivity(inputsymintent)
        }
    }
}