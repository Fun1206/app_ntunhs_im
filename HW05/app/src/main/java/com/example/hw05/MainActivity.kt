package com.example.hw05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_g1 = findViewById<Button>(R.id.btn_guessNum)
        val btn_g2 = findViewById<Button>(R.id.btn_PSR)

        btn_g1.setOnClickListener {
            var oneIntent = Intent(this,Game1_gusnum::class.java)
            startActivity(oneIntent)
        }
        btn_g2.setOnClickListener {
            var seconIntent = Intent(this,Game2_PSR::class.java)
            startActivity(seconIntent)
        }

    }

}