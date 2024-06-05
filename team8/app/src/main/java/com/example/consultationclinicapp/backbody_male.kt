package com.example.consultationclinicapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView

class backbody_male : AppCompatActivity() {
    private lateinit var dbHelper: SQLiteOpenHelper
    private val PREFS_NAME = "language_prefs"
    private val KEY_LANGUAGE = "language_key"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backbody_male)
        dbHelper = SQLiteOpenHelper(this)

        val home = findViewById<ImageButton>(R.id.home_btn)
        val frontBtn = findViewById<Button>(R.id.front_btn)
        val backBtn = findViewById<Button>(R.id.back_btn)
        val previousBtn = findViewById<Button>(R.id.previous_btn)
        val nextBtn = findViewById<Button>(R.id.next2_btn)
        val titleTextView = findViewById<TextView>(R.id.title_textView)
        val selectTextView = findViewById<TextView>(R.id.textView)

        // 初始化CheckBoxes
        val checkBoxes = listOf(
            findViewById<CheckBox>(R.id.head_2),
            findViewById<CheckBox>(R.id.neck_2),
            findViewById<CheckBox>(R.id.back),
            findViewById<CheckBox>(R.id.waist),
            findViewById<CheckBox>(R.id.Buttocks),
            findViewById<CheckBox>(R.id.legs_2),
            findViewById<CheckBox>(R.id.feet_2),
            findViewById<CheckBox>(R.id.whole_body_2),
            findViewById<CheckBox>(R.id.hand_2),
            findViewById<CheckBox>(R.id.skin_2),
            findViewById<CheckBox>(R.id.psychology_2)
        )

        val checkBoxKeyMap = mapOf(
            R.id.head_2 to "head",
            R.id.neck_2 to "neck",
            R.id.back to "back",
            R.id.waist to "waist",
            R.id.Buttocks to "Buttocks",
            R.id.legs_2 to "legs",
            R.id.feet_2 to "feet",
            R.id.whole_body_2 to "whole_body",
            R.id.hand_2 to "hand",
            R.id.skin_2 to "skin",
            R.id.psychology_2 to "psychology"
        )

        // 獲取 SharedPreferences
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isEnglish = sharedPreferences.getBoolean(KEY_LANGUAGE, false)

        // 根據值更新UI
        updateUI(isEnglish, titleTextView, selectTextView, frontBtn, backBtn, previousBtn, nextBtn, checkBoxes)

        // 設置每個 CheckBox 的監聽器
        checkBoxes.forEach { checkBox ->
            val key = checkBoxKeyMap[checkBox.id]
            (checkBox as? CheckBox)?.setOnCheckedChangeListener { _, isChecked ->
                if (key != null) {
                    val editor = sharedPreferences.edit()
                    editor.putBoolean(key, isChecked)
                    editor.apply() // 立即提交更新
                }
            }
        }

        home.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }

        frontBtn.setOnClickListener {
            val frontMaleIntent = Intent(this, frontbody_male::class.java)
            startActivity(frontMaleIntent)
        }

        previousBtn.setOnClickListener {
            val inputIntent = Intent(this, analyze_input::class.java)
            startActivity(inputIntent)
        }

        nextBtn.setOnClickListener {
            val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
            val anyPrefsTrue = prefs.all.entries.any { it.value as? Boolean ?: false }
            val anyCheckBoxChecked = checkBoxes.any { it.isChecked }

            if (anyPrefsTrue || anyCheckBoxChecked) {
                val selectedTags = checkBoxes.filter { it.isChecked }.map { it.tag.toString() }
                val inputSymIntent = Intent(this, SubParts_input::class.java).apply {
                    putExtra("BodyPartID", ArrayList(selectedTags))
                    putExtra("type", "MaleBack")
                }
                startActivity(inputSymIntent)
            } else {
                // 沒有任何選項被選擇，顯示 AlertDialog
                AlertDialog.Builder(this)
                    .setTitle(if (isEnglish) "Alert" else "提示")
                    .setMessage(if (isEnglish) "Please select at least one part" else "請點選至少一種部位")
                    .setPositiveButton(if (isEnglish) "OK" else "確定") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }
    private fun updateUI(
        isEnglish: Boolean,
        titleTextView: TextView,
        selectTextView: TextView,
        frontBtn: Button,
        backBtn: Button,
        previousBtn: Button,
        nextBtn: Button,
        checkBoxes: List<CheckBox>
    ) {
        if (isEnglish) {
            titleTextView.text = "Symptom Analysis"
            titleTextView.textSize = 30f
            selectTextView.text = "Select the uncomfortable body part"
            selectTextView.textSize = 20f
            frontBtn.text = "Front"
            backBtn.text = "Back"
            previousBtn.text = "Previous"
            nextBtn.text = "Next"
            checkBoxes.forEachIndexed { index, checkBox ->
                val resultList = dbHelper.getBodyPartsByBodyPartID(checkBox.tag.toString().toInt())
                if (resultList.isNotEmpty()) {
                    checkBox.text = resultList[0].En_PartName
                    checkBox.textSize = 12f
                }
            }
        } else {
            titleTextView.text = "症狀分析"
            titleTextView.textSize = 34f
            selectTextView.text = "選擇不舒服的身體部位"
            selectTextView.textSize = 24f
            frontBtn.text = "前面"
            backBtn.text = "後面"
            previousBtn.text = "上一步"
            nextBtn.text = "下一步"
            checkBoxes.forEachIndexed { index, checkBox ->
                val resultList = dbHelper.getBodyPartsByBodyPartID(checkBox.tag.toString().toInt())
                if (resultList.isNotEmpty()) {
                    checkBox.text = resultList[0].PartName
                    checkBox.textSize = 14f
                }
            }
        }
    }
    // 儲存特有的 CheckBox 狀態到 SharedPreferences
    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("back", findViewById<CheckBox>(R.id.back).isChecked)
        editor.putBoolean("waist", findViewById<CheckBox>(R.id.waist).isChecked)
        editor.putBoolean("buttocks", findViewById<CheckBox>(R.id.Buttocks).isChecked)
        // 保存共用的 CheckBox 狀態
        editor.putBoolean("head", findViewById<CheckBox>(R.id.head_2).isChecked)
        editor.putBoolean("neck", findViewById<CheckBox>(R.id.neck_2).isChecked)
        editor.putBoolean("legs", findViewById<CheckBox>(R.id.legs_2).isChecked)
        editor.putBoolean("feet", findViewById<CheckBox>(R.id.feet_2).isChecked)
        editor.putBoolean("whole_body", findViewById<CheckBox>(R.id.whole_body_2).isChecked)
        editor.putBoolean("hands", findViewById<CheckBox>(R.id.hand_2).isChecked)
        editor.putBoolean("skin", findViewById<CheckBox>(R.id.skin_2).isChecked)
        editor.putBoolean("psychology", findViewById<CheckBox>(R.id.psychology_2).isChecked)
        editor.apply()
    }

    // 從 SharedPreferences 讀取特有的 CheckBox 狀態
    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        findViewById<CheckBox>(R.id.back).isChecked = prefs.getBoolean("back", false)
        findViewById<CheckBox>(R.id.waist).isChecked = prefs.getBoolean("waist", false)
        findViewById<CheckBox>(R.id.Buttocks).isChecked = prefs.getBoolean("buttocks", false)

        // 恢復共用的 CheckBox 狀態
        findViewById<CheckBox>(R.id.head_2).isChecked = prefs.getBoolean("head", false)
        findViewById<CheckBox>(R.id.neck_2).isChecked = prefs.getBoolean("neck", false)
        findViewById<CheckBox>(R.id.legs_2).isChecked = prefs.getBoolean("legs", false)
        findViewById<CheckBox>(R.id.feet_2).isChecked = prefs.getBoolean("feet", false)
        findViewById<CheckBox>(R.id.whole_body_2).isChecked = prefs.getBoolean("whole_body", false)
        findViewById<CheckBox>(R.id.hand_2).isChecked = prefs.getBoolean("hands", false)
        findViewById<CheckBox>(R.id.skin_2).isChecked = prefs.getBoolean("skin", false)
        findViewById<CheckBox>(R.id.psychology_2).isChecked = prefs.getBoolean("psychology", false)
    }

}