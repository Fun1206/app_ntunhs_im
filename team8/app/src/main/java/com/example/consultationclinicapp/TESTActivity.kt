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

        val btnTest = findViewById<Button>(R.id.btnTest)

        btnTest.setOnClickListener {
            testDatabaseFunctions()
        }

        val selectedParts = intent.getStringArrayListExtra("selected_parts")
        val frontback = intent.getIntExtra("front_back", -1)

        if (selectedParts != null && selectedParts.size > 1 && frontback != -1) {
            val bodyType = dbHelper.getBodyTypeByPartNameAndPosition(selectedParts[1], frontback)
            results2TextView.text = bodyType.toString()
        }

        resultsTextView.text = selectedParts?.joinToString(separator = "\n")
    }

    private fun testDatabaseFunctions() {
        // 測試getBodyPartsByTypeAndPosition
        val bodyParts = dbHelper.getBodyPartsByTypeAndPosition(0, 0)
        val bodyPartsResults = bodyParts.joinToString(separator = "\n") { part ->
            "ID: ${part.partId}, Name: ${part.partName}"
        }

        // 測試getDetailPartsByPartId
        val detailParts = dbHelper.getDetailPartsByPartId(1)
        val detailPartsResults = detailParts.joinToString(separator = "\n") { part ->
            "Detail ID: ${part.detailPartId}, Name: ${part.detailPartName}"
        }

        resultsTextView.text = "Body Parts:\n$bodyPartsResults\n\nDetail Parts:\n$detailPartsResults"
    }
}