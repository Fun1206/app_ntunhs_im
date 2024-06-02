package com.example.consultationclinicapp

import android.app.AlertDialog
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

        setupListeners(home, previous, gender, next, container)
    }

    private fun addSelectedPartsBySide(prefs: SharedPreferences, side: Int, selectedParts: MutableList<String>) {
        val partsMap = if (side == 0) {
            mapOf("back" to "背部", "waist" to "腰部", "buttocks" to "臀部")
        } else {
            mapOf("chest" to "胸部", "abdomen" to "腹部", "lower_abdomen" to "下腹部")
        }
        partsMap.forEach { (key, value) ->
            if (prefs.getBoolean(key, false)) selectedParts.add(value)
        }
    }

    private fun setupListeners(home: ImageButton, previous: Button, gender: String?, next: Button, container: LinearLayout) {
        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        previous.setOnClickListener {
            startActivity(Intent(this, if (gender == "male") frontbody_male::class.java else frontbody_female::class.java))
        }
        next.setOnClickListener {
            // 從LinearLayout容器中檢索所有已勾選的CheckBox的標籤 (即SubPartID)
            val selectedSubPartIDs = mutableListOf<Int>()
            val selectedSubParts = mutableListOf<String>()
            for (i in 0 until container.childCount) {
                val view = container.getChildAt(i)
                if (view is CheckBox && view.isChecked) {
                    selectedSubPartIDs.add(view.tag as Int)  // 收集標籤中的唯一ID
                    selectedSubParts.add(view.text.toString())  // 收集文本
                }
            }

            if (selectedSubPartIDs.isEmpty()) {
                // 沒有選中任何CheckBox，顯示AlertDialog
                AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("請點選至少一種細節部位")
                    .setPositiveButton("確定") { dialog, _ -> dialog.dismiss() }
                    .show()
            } else {
                // 有選中的CheckBox，進行頁面跳轉
                val intent = Intent(this, Symptoms_input::class.java)
                intent.putStringArrayListExtra("selectedSubParts", ArrayList(selectedSubParts))
                intent.putIntegerArrayListExtra("selectedSubPartIDs", ArrayList(selectedSubPartIDs))
                startActivity(intent)
            }
        }
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
                // 設置標籤來儲存SubPartID
                setTag(part.SubPartID)
            })
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
