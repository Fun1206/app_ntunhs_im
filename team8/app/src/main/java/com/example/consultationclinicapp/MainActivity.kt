package com.example.consultationclinicapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch

class MainActivity : AppCompatActivity() {
    private lateinit var languageSwitch: Switch
    private lateinit var analyzeBtn: Button
    private lateinit var searchBtn: Button
    private lateinit var imageView: ImageView
    private lateinit var sharedPreferences: SharedPreferences
    private val PREFS_NAME = "language_prefs"
    private val KEY_LANGUAGE = "language_key"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setupListeners()
    }
    private fun initViews() {
        analyzeBtn = findViewById(R.id.analyze_btn)
        searchBtn = findViewById(R.id.search_btn)
        imageView = findViewById(R.id.illustrate_Img)
        languageSwitch = findViewById(R.id.languageSwitch)

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isEnglish = sharedPreferences.getBoolean(KEY_LANGUAGE, false)
        setupLanguageSwitch(isEnglish)
    }
    private fun setupListeners() {
        analyzeBtn.setOnClickListener {
            startActivity(Intent(this, analyze_input::class.java)) }
        searchBtn.setOnClickListener {
            startActivity(Intent(this, search_drugs::class.java)) }
    }
    private fun setupLanguageSwitch(isEnglish: Boolean) {
        languageSwitch.isChecked = isEnglish
        updateUI(isEnglish)

        languageSwitch.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean(KEY_LANGUAGE, isChecked)
                apply()
            }
            updateUI(isChecked)
        }
    }

    private fun updateUI(isEnglish: Boolean) {
        languageSwitch.text = if (isEnglish) getString(R.string.language_english)
            else getString(R.string.language_chinese)
        findViewById<Button>(R.id.analyze_btn).apply {
            text = if (isEnglish) getString(R.string.analyze_english)
            else getString(R.string.analyze_chinese)
            textSize = if (isEnglish) 16f else 20f
        }
        findViewById<Button>(R.id.search_btn).apply {
            text = if (isEnglish) getString(R.string.search_english)
            else getString(R.string.search_chinese)
            textSize = if (isEnglish) 16f else 20f
        }
        findViewById<ImageView>(R.id.illustrate_Img).setImageResource(
            if (isEnglish) R.drawable.illustrate_en else R.drawable.illustrate
        )
    }
}
