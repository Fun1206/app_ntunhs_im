package com.example.consultationclinicapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

class SubParts_input : AppCompatActivity() {
    private lateinit var dbHelper: SQLiteOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subparts_input)
        dbHelper = SQLiteOpenHelper(this)

        val home = findViewById<ImageButton>(R.id.home_btn)
        val previous = findViewById<Button>(R.id.previous3_btn)
        val next = findViewById<Button>(R.id.next3_btn)

        val container = findViewById<LinearLayout>(R.id.checkboxContainer)
        container.removeAllViews()
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val gender = intent.getStringExtra("gender")
        val selectedParts = intent.getStringArrayListExtra("selected_parts") ?: arrayListOf()
        val side = intent.getIntExtra("side", -1)

        addSelectedPartsBySide(prefs, side, selectedParts)

        selectedParts.forEach { part ->
            dbHelper.getBodyPartIDByPartNameAndGender(part, if (gender == "male") 0 else 1)?.let { bodyPartId ->
                dbHelper.getSubPartsByPartId(bodyPartId)?.let { subParts ->
                    displayOptions(subParts, part, container)
                }
            }
        }

        setupListeners(home, previous, gender, next)
    }

    private fun addSelectedPartsBySide(prefs: SharedPreferences, side: Int, selectedParts: MutableList<String>) {
        val partsMap = if (side == 0) {
            mapOf("back" to "Back", "waist" to "Waist", "buttocks" to "Buttocks")
        } else {
            mapOf("chest" to "胸部", "abdomen" to "腹部", "lower_abdomen" to "下腹部")
        }
        partsMap.forEach { (key, value) ->
            if (prefs.getBoolean(key, false)) selectedParts.add(value)
        }
    }

    private fun setupListeners(home: ImageButton, previous: Button, gender: String?, next: Button) {
        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        previous.setOnClickListener {
            startActivity(Intent(this, if (gender == "male") frontbody_male::class.java else frontbody_female::class.java))
        }
        next.setOnClickListener { startActivity(Intent(this, Symptoms_input::class.java)) }
    }

    private fun displayOptions(bodyParts: List<SubPart>, partName: String, container: LinearLayout) {
        val textView = TextView(this).apply {
            text = partName
            textSize = 24f
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                setMargins(0, 16.toPx(), 0, 16.toPx())
            }
        }
        container.addView(textView)

        bodyParts.forEach { part ->
            container.addView(CheckBox(this).apply {
                text = part.SubPartName
                textSize = 20f
                setTypeface(null, Typeface.BOLD)
                setBackgroundResource(R.drawable.checkbox_background)
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    setMargins(0, 8.toPx(), 0, 8.toPx())
                }
            })
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
