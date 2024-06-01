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

            if (selectedParts != null && selectedParts.size > 1 && frontback != -1) {
                var bodyType = dbHelper.getBodyTypeByPartNameAndPosition(selectedParts[0], frontback)

                var detailPartNames = bodyType?.let { it1 ->
                    dbHelper.getDetailPartsByPartId(it1).map { it.detailPartName }.toTypedArray()
                } ?: arrayOf<String>()
                resultsTextView.text = detailPartNames[6].toString()

                bodyType = dbHelper.getBodyTypeByPartNameAndPosition(selectedParts[1], frontback)
                detailPartNames = bodyType?.let { it1 ->
                    dbHelper.getDetailPartsByPartId(it1).map { it.detailPartName }.toTypedArray()
                } ?: arrayOf<String>()
                results2TextView.text = detailPartNames[0].toString()
            }

        }

       // resultsTextView.text = selectedParts?.joinToString(separator = "\n")
    }

    private fun testDatabaseFunctions() {
        // 測試getBodyPartsByTypeAndPosition
        val bodyParts = dbHelper.getBodyPartsByTypeAndPosition(0, 0)
        val bodyPartsResults = bodyParts.joinToString(separator = "\n") { part ->
            "ID: ${part.BodyPartID}, Name: ${part.PartName}"
        }

        // 測試getDetailPartsByPartId
        val detailParts = dbHelper.getDetailPartsByPartId(1)
        val detailPartsResults = detailParts.joinToString(separator = "\n") { part ->
            "Detail ID: ${part.detailPartId}, Name: ${part.detailPartName}"
        }

        //resultsTextView.text = "Body Parts:\n$bodyPartsResults\n\nDetail Parts:\n$detailPartsResults"
    }
}