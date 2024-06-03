package com.example.consultationclinicapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class analyze_input : AppCompatActivity() {
    private val PREFS_NAME = "language_prefs"
    private val KEY_LANGUAGE = "language_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyze_input)

        val home = findViewById<ImageButton>(R.id.home_btn)
        val next = findViewById<Button>(R.id.next_btn)
        val gender = findViewById<RadioGroup>(R.id.gender)
        val male = findViewById<RadioButton>(R.id.male)
        val female = findViewById<RadioButton>(R.id.female)
        val titleTextView = findViewById<TextView>(R.id.title_textView)
        val genderTxt = findViewById<TextView>(R.id.gender_txt)
        val ageTxt = findViewById<TextView>(R.id.age)
        val spn_age = findViewById<Spinner>(R.id.age_spn)

        // 加載SharedPreferences中的設定值
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isEnglish = sharedPreferences.getBoolean(KEY_LANGUAGE, false)

        // 根據值更新UI
        updateUI(isEnglish, titleTextView, genderTxt, ageTxt, male, female, next, spn_age)

        // 清除 SharedPreferences
        clearPreferences()

        home.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }

        next.setOnClickListener {
            val selectedGenderId = gender.checkedRadioButtonId
            if (selectedGenderId == -1) {
                // 顯示提示框
                AlertDialog.Builder(this)
                    .setTitle(if (isEnglish) "Alert" else "提示")
                    .setMessage(if (isEnglish) "Please select a gender option" else "請選擇性別選項")
                    .setPositiveButton(if (isEnglish) "OK" else "確定", null)
                    .show()
            } else {
                // 根據選中的RadioButton跳轉到不同的Activity
                val bodyIntent = if (male.isChecked) {
                    Intent(this, frontbody_male::class.java)
                } else {
                    Intent(this, frontbody_female::class.java)
                }
                startActivity(bodyIntent)
            }
        }

        // 設置 Spinner 的選項選擇監聽器
        spn_age.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                if (view != null) {
                    // 獲取選擇的項目
                    val age = resources.getStringArray(if (isEnglish) R.array.age_en else R.array.age)[pos]  // 獲取選中的年齡
                    // 可在此處添加更多基於選中年齡的操作
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 這裡可以處理沒有選擇任何項目的情況
            }
        }
    }

    private fun clearPreferences() {
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    private fun updateUI(
        isEnglish: Boolean,
        titleTextView: TextView,
        genderTxt: TextView,
        ageTxt: TextView,
        male: RadioButton,
        female: RadioButton,
        next: Button,
        spn_age: Spinner
    ) {
        if (isEnglish) {
            titleTextView.text = "Symptom Analysis"
            genderTxt.text = "Gender:"
            ageTxt.text = "Age:"
            male.text = "Male"
            female.text = "Female"
            female.textSize = 16f
            male.textSize = 16f
            next.text = "Next"
            val adapter = ArrayAdapter.createFromResource(this, R.array.age_en, R.layout.spinner_item)
            adapter.setDropDownViewResource(R.layout.spinner_item)
            spn_age.adapter = adapter
        } else {
            titleTextView.text = "症狀分析"
            genderTxt.text = "性別："
            ageTxt.text = "年齡："
            male.text = "男"
            female.text = "女"
            female.textSize = 20f
            male.textSize = 20f
            next.text = "下一步"
            val adapter = ArrayAdapter.createFromResource(this, R.array.age, R.layout.spinner_item)
            adapter.setDropDownViewResource(R.layout.spinner_item)
            spn_age.adapter = adapter
        }
    }
}
