package com.example.consultationclinicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class symptom_input : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptom_input)
        val home = findViewById<ImageButton>(R.id.home_btn)
        val pervious = findViewById<Button>(R.id.previous3_btn)
        val next = findViewById<Button>(R.id.next3_btn)
        // 接收傳遞的數據
        val receivedValue = intent.getStringExtra("pervious_record")

        home.setOnClickListener {
            var homeintent = Intent(this,MainActivity::class.java)
            startActivity(homeintent)
        }
        pervious.setOnClickListener {
            val bodyintent = if (receivedValue == "male") {
                Intent(this,frontbody_male::class.java)
            }else{
                Intent(this,frontbody_female::class.java)
            }
            startActivity(bodyintent)
        }
        next.setOnClickListener {
            var homeintent = Intent(this,MainActivity::class.java)
            startActivity(homeintent)

        }

    }
}