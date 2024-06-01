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
import androidx.appcompat.app.AlertDialog

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
        val genderCode = when (gender) {
            "male" -> 0
            "female" -> 1
            else -> -1  // 如果gender不是male或female，可以返回-1作為錯誤代碼或其他處理
        }
        val selectedParts = intent.getStringArrayListExtra("selected_parts") ?: arrayListOf()
        val side = intent.getIntExtra("side", -1)

        addSelectedPartsBySide(prefs, side, selectedParts)

        selectedParts.forEach { part ->
            dbHelper.getBodyPartIDByPartNameAndGender(part, genderCode)?.let { bodyPartId ->
                dbHelper.getSubPartsByPartId(bodyPartId)?.let { subParts ->
                    displayOptions(subParts, part, container)
                }
            }
        }

        setupListeners(home, previous, gender, next)
    }

    private fun addSelectedPartsBySide(prefs: SharedPreferences, side: Int, selectedParts: MutableList<String>) {
        if (side == 0) {
            // 顯示提示框
            AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("正面")
                .setPositiveButton("確定", null)
                .show()
            if (prefs.getBoolean("back", false) || prefs.getBoolean("waist", false) || prefs.getBoolean("buttocks", false)) {
                if (prefs.getBoolean("back", false)) selectedParts.add("Back")
                if (prefs.getBoolean("waist", false)) selectedParts.add("Waist")
                if (prefs.getBoolean("buttocks", false)) selectedParts.add("Buttocks")
            }
        } else if (side == 1) {
            // 顯示提示框
            AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("反面")
                .setPositiveButton("確定", null)
                .show()
            if (prefs.getBoolean("chest", false) || prefs.getBoolean("abdomen", false) || prefs.getBoolean("lower_abdomen", false)) {
                if (prefs.getBoolean("chest", false)) selectedParts.add("Chest")
                if (prefs.getBoolean("abdomen", false)) selectedParts.add("Abdomen")
                if (prefs.getBoolean("lower_abdomen", false)) selectedParts.add("Lower Abdomen")
            }
        }

        val partsMessage = selectedParts?.joinToString(separator = ", ") {
            it  // 這裡可以添加進一步的格式化，如果需要
        } ?: "未選擇任何部位"

        // 創建並顯示一個提示窗
        AlertDialog.Builder(this)
            .setTitle("選擇的部位")  // 設置提示窗的標題
            .setMessage(partsMessage)  // 設置顯示的消息為選擇的部位
            .setPositiveButton("確定") { dialog, which ->
                // 當點擊確定按鈕時的行為，這裡可以根據需要進行自定義
                dialog.dismiss()
            }
            .show()  // 顯示提示窗

    }
    private fun setupListeners(home: ImageButton, previous: Button, gender: String?, next: Button) {
        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        previous.setOnClickListener {
            val intent = if (gender == "male") Intent(this, frontbody_male::class.java)
            else Intent(this, frontbody_female::class.java)
            startActivity(intent)
        }
        next.setOnClickListener {
            startActivity(Intent(this, Symptoms_input::class.java))
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
            })
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
