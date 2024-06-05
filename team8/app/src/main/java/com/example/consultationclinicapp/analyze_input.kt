package com.example.consultationclinicapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class analyze_input : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var titleTextView: TextView
    private lateinit var genderTxt: TextView
    private lateinit var ageTxt: TextView
    private lateinit var maleRadioButton: RadioButton
    private lateinit var femaleRadioButton: RadioButton
    private lateinit var nextButton: Button
    private lateinit var ageSpinner: Spinner

    private val PREFS_NAME = "language_prefs"
    private val KEY_LANGUAGE = "language_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyze_input)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        // 清除 SharedPreferences
        clearPreferences()
        titleTextView = findViewById(R.id.title_textView)
        genderTxt = findViewById(R.id.gender_txt)
        ageTxt = findViewById(R.id.age)
        maleRadioButton = findViewById(R.id.male)
        femaleRadioButton = findViewById(R.id.female)
        nextButton = findViewById(R.id.next_btn)
        ageSpinner = findViewById(R.id.age_spn)

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isEnglish = sharedPreferences.getBoolean(KEY_LANGUAGE, false)
        updateUI(isEnglish)
    }
    private fun clearPreferences() {
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
    private fun setupListeners() {
        findViewById<ImageButton>(R.id.home_btn).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        nextButton.setOnClickListener {
            val selectedGenderId = findViewById<RadioGroup>(R.id.gender).checkedRadioButtonId
            if (selectedGenderId == -1) {
                showAlertDialog("Please select a gender option", "請選擇性別選項")
            } else {
                val intent =
                    if (maleRadioButton.isChecked) Intent(this, frontbody_male::class.java)
                    else Intent(this, frontbody_female::class.java)
                startActivity(intent)
            }
        }

        ageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // 選擇年齡後的處理
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun showAlertDialog(englishMessage: String, chineseMessage: String) {
        AlertDialog.Builder(this)
            .setTitle(if (sharedPreferences.getBoolean(KEY_LANGUAGE, false)) "Alert" else "提示")
            .setMessage(if (sharedPreferences.getBoolean(KEY_LANGUAGE, false)) englishMessage else chineseMessage)
            .setPositiveButton(if (sharedPreferences.getBoolean(KEY_LANGUAGE, false)) "OK" else "確定", null)
            .show()
    }

    private fun updateUI(isEnglish: Boolean) {
        titleTextView.text = getString(if (isEnglish) R.string.analyze_english else R.string.analyze_chinese)
        genderTxt.text = getString(if (isEnglish) R.string.gender_english else R.string.gender)
        ageTxt.text = getString(if (isEnglish) R.string.age_english else R.string.age)
        maleRadioButton.text = getString(if (isEnglish) R.string.male_english else R.string.male)
        femaleRadioButton.text = getString(if (isEnglish) R.string.female_english else R.string.female)
        nextButton.text = getString(if (isEnglish) R.string.next_english else R.string.next)
        setupSpinner(isEnglish)
    }

    private fun setupSpinner(isEnglish: Boolean) {
        val adapter = ArrayAdapter.createFromResource(
            this,
            if (isEnglish) R.array.age_en else R.array.age,
            R.layout.spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner.adapter = adapter
    }
}
