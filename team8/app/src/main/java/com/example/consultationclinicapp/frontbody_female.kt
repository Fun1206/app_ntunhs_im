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

class frontbody_female : AppCompatActivity() {
    private lateinit var dbHelper: SQLiteOpenHelper
    private val PREFS_NAME = "language_prefs"
    private val KEY_LANGUAGE = "language_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frontbody_female)
        dbHelper = SQLiteOpenHelper(this)
        val home = findViewById<ImageButton>(R.id.home_btn)
        val frontBtn = findViewById<Button>(R.id.frontf_btn)
        val backBtn = findViewById<Button>(R.id.backf_btn)
        val previousBtn = findViewById<Button>(R.id.previous_btn)
        val nextBtn = findViewById<Button>(R.id.next2_btn)
        val titleTextView = findViewById<TextView>(R.id.title_textView)
        val selectTextView = findViewById<TextView>(R.id.textView)

        // 初始化CheckBoxes
        val checkBoxes = listOf(
            findViewById<CheckBox>(R.id.head_f1),
            findViewById<CheckBox>(R.id.neck_f1),
            findViewById<CheckBox>(R.id.chest_f),
            findViewById<CheckBox>(R.id.abdomen_f),
            findViewById<CheckBox>(R.id.lower_abdomen_f),
            findViewById<CheckBox>(R.id.legs_f1),
            findViewById<CheckBox>(R.id.feet_f1),
            findViewById<CheckBox>(R.id.whole_body_f1),
            findViewById<CheckBox>(R.id.hand_f1),
            findViewById<CheckBox>(R.id.skin_f1),
            findViewById<CheckBox>(R.id.psychology_f1)
        )

        val checkBoxKeyMap = mapOf(
            R.id.head_f1 to "head",
            R.id.neck_f1 to "neck",
            R.id.chest_f to "chest",
            R.id.abdomen_f to "abdomen",
            R.id.lower_abdomen_f to "lower_abdomen",
            R.id.legs_f1 to "legs",
            R.id.feet_f1 to "feet",
            R.id.whole_body_f1 to "whole_body",
            R.id.hand_f1 to "hand",
            R.id.skin_f1 to "skin",
            R.id.psychology_f1 to "psychology"
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

        backBtn.setOnClickListener {
            val backFemaleIntent = Intent(this, backbody_female::class.java)
            startActivity(backFemaleIntent)
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
                    putExtra("gender", "female")
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
            selectTextView.text = "Select the uncomfortable body part"
            frontBtn.text = "Front"
            backBtn.text = "Back"
            previousBtn.text = "Previous"
            nextBtn.text = "Next"
            checkBoxes.forEachIndexed { index, checkBox ->
                val resultList = dbHelper.getBodyPartsByBodyPartID(checkBox.tag.toString().toInt())
                if (resultList.isNotEmpty()) {
                    checkBox.text = resultList[0].En_PartName
                }
            }
        } else {
            titleTextView.text = "症狀分析"
            selectTextView.text = "選擇不舒服的身體部位"
            frontBtn.text = "前面"
            backBtn.text = "後面"
            previousBtn.text = "上一步"
            nextBtn.text = "下一步"
            checkBoxes.forEachIndexed { index, checkBox ->
                val resultList = dbHelper.getBodyPartsByBodyPartID(checkBox.tag.toString().toInt())
                if (resultList.isNotEmpty()) {
                    checkBox.text = resultList[0].PartName
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("chest", findViewById<CheckBox>(R.id.chest_f).isChecked)
        editor.putBoolean("abdomen", findViewById<CheckBox>(R.id.abdomen_f).isChecked)
        editor.putBoolean("lower_abdomen", findViewById<CheckBox>(R.id.lower_abdomen_f).isChecked)

        // 保存共用的 CheckBox 狀態
        editor.putBoolean("head", findViewById<CheckBox>(R.id.head_f1).isChecked)
        editor.putBoolean("neck", findViewById<CheckBox>(R.id.neck_f1).isChecked)
        editor.putBoolean("legs", findViewById<CheckBox>(R.id.legs_f1).isChecked)
        editor.putBoolean("feet", findViewById<CheckBox>(R.id.feet_f1).isChecked)
        editor.putBoolean("whole_body", findViewById<CheckBox>(R.id.whole_body_f1).isChecked)
        editor.putBoolean("hands", findViewById<CheckBox>(R.id.hand_f1).isChecked)
        editor.putBoolean("skin", findViewById<CheckBox>(R.id.skin_f1).isChecked)
        editor.putBoolean("psychology", findViewById<CheckBox>(R.id.psychology_f1).isChecked)
        editor.apply()
    }

    // 從 SharedPreferences 讀取特有的 CheckBox 狀態
    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        findViewById<CheckBox>(R.id.chest_f).isChecked = prefs.getBoolean("chest", false)
        findViewById<CheckBox>(R.id.abdomen_f).isChecked = prefs.getBoolean("abdomen", false)
        findViewById<CheckBox>(R.id.lower_abdomen_f).isChecked = prefs.getBoolean("lower_abdomen", false)

        // 恢復共用的 CheckBox 狀態
        findViewById<CheckBox>(R.id.head_f1).isChecked = prefs.getBoolean("head", false)
        findViewById<CheckBox>(R.id.neck_f1).isChecked = prefs.getBoolean("neck", false)
        findViewById<CheckBox>(R.id.legs_f1).isChecked = prefs.getBoolean("legs", false)
        findViewById<CheckBox>(R.id.feet_f1).isChecked = prefs.getBoolean("feet", false)
        findViewById<CheckBox>(R.id.whole_body_f1).isChecked = prefs.getBoolean("whole_body", false)
        findViewById<CheckBox>(R.id.hand_f1).isChecked = prefs.getBoolean("hands", false)
        findViewById<CheckBox>(R.id.skin_f1).isChecked = prefs.getBoolean("skin", false)
        findViewById<CheckBox>(R.id.psychology_f1).isChecked = prefs.getBoolean("psychology", false)
    }
}