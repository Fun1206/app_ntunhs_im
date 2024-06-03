package com.example.consultationclinicapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch

class MainActivity : AppCompatActivity() {
    private lateinit var languageSwitch: Switch
    private val PREFS_NAME = "language_prefs"
    private val KEY_LANGUAGE = "language_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val analyze_btn = findViewById<Button>(R.id.analyze_btn)
        val search_btn = findViewById<Button>(R.id.search_btn)
        val imageView = findViewById<ImageView>(R.id.illustrate_Img)

        languageSwitch = findViewById(R.id.languageSwitch)

        // 加載SharedPreferences中的設定值
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var isEnglish = sharedPreferences.getBoolean(KEY_LANGUAGE, false)
        languageSwitch.isChecked = isEnglish
        updateSwitchText(isEnglish)

        // 根據值更新UI
        updateUI(isEnglish, analyze_btn, search_btn, imageView)

        // 設置Switch元件的監聽器
        languageSwitch.setOnCheckedChangeListener { _, isChecked ->
            // 保存設定值到SharedPreferences，true表示“英文”，false表示“中文”
            val editor = sharedPreferences.edit()
            editor.putBoolean(KEY_LANGUAGE, isChecked)
            editor.apply()

            // 更新Switch元件的顯示文本
            updateSwitchText(isChecked)

            // 更新UI
            updateUI(isChecked, analyze_btn, search_btn, imageView)
        }

        analyze_btn.setOnClickListener {
            var analyzeInputintent = Intent(this, analyze_input::class.java)
            startActivity(analyzeInputintent)
        }

        search_btn.setOnClickListener {
            var searchintent = Intent(this, search_drugs::class.java)
            startActivity(searchintent)
        }
    }

    private fun updateSwitchText(isEnglish: Boolean) {
        if (isEnglish) {
            languageSwitch.text = languageSwitch.textOn
        } else {
            languageSwitch.text = languageSwitch.textOff
        }
    }

    private fun updateUI(isEnglish: Boolean, analyze_btn: Button, search_btn: Button, imageView: ImageView) {
        if (isEnglish) {
            analyze_btn.text = "Symptom Analysis"
            search_btn.text = "Drug Inquiry"
            analyze_btn.textSize = 16f
            search_btn.textSize = 16f
            imageView.setImageResource(R.drawable.illustrate_en) // 英文圖片資源
        } else {
            analyze_btn.text = "症狀分析"
            search_btn.text = "查詢藥物"
            analyze_btn.textSize = 20f
            search_btn.textSize = 20f
            imageView.setImageResource(R.drawable.illustrate) // 中文圖片資源
        }
    }
}
