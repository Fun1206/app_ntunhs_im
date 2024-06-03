package com.example.consultationclinicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton

class search_drugs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_drugs)

        val home = findViewById<ImageButton>(R.id.home_btn)
        val search = findViewById<ImageButton>(R.id.search_btn)
        val keyword = findViewById<EditText>(R.id.search_field)
        home.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }
        search.setOnClickListener {
            
        }
    }
}