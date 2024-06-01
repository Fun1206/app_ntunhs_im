package com.example.consultationclinicapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TESTActivity : AppCompatActivity() {
    private lateinit var dbHelper: SQLiteOpenHelper
    private lateinit var resultsTextView: TextView
    private lateinit var results2TextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testactivity)

        dbHelper = SQLiteOpenHelper(this)
        resultsTextView = findViewById(R.id.tvResults)
        results2TextView = findViewById(R.id.tvResults2)
        val selectedParts = intent.getStringArrayListExtra("selected_parts")
        val frontback = intent.getIntExtra("front_back", -1)
        val btnTest = findViewById<Button>(R.id.btnTest)

        btnTest.setOnClickListener {
            //testDatabaseFunctions()


            }

        }

       // resultsTextView.text = selectedParts?.joinToString(separator = "\n")
    }


