package com.example.consultationclinicapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton

class frontbody_male : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frontbody_male)

        val home = findViewById<ImageButton>(R.id.home_btn)
        // val front_btn = findViewById<Button>(R.id.front_btn)
        val back_btn = findViewById<Button>(R.id.back_btn)
        val pervious = findViewById<Button>(R.id.previous_btn)
        val next = findViewById<Button>(R.id.next2_btn)

        // 初始化CheckBoxes
        val checkBoxes = listOf<CheckBox>(
            findViewById(R.id.head_1),
            findViewById(R.id.neck_1),
            findViewById(R.id.chest),
            findViewById(R.id.abdomen),
            findViewById(R.id.lower_abdomen),
            findViewById(R.id.legs_1),
            findViewById(R.id.feet_1),
            findViewById(R.id.whole_body_1),
            findViewById(R.id.hand_1),
            findViewById(R.id.skin_1),
            findViewById(R.id.psychology_1)
        )

        home.setOnClickListener {
            var homeintent = Intent(this,MainActivity::class.java)
            startActivity(homeintent)
        }

        /*front_btn.setOnClickListener {
            var frontmaleintent = Intent(this,frontbody_male::class.java)
            startActivity(frontmaleintent)
        }*/

        back_btn.setOnClickListener {
            var backmaleintent = Intent(this,backbody_male::class.java)
            startActivity(backmaleintent)
        }

        pervious.setOnClickListener {
            var inputintent = Intent(this,analyze_input::class.java)
            startActivity(inputintent)
        }

        next.setOnClickListener {
            val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
            val anyPrefsTrue = prefs.all.entries.any { it.value as? Boolean ?: false }
            val anyCheckBoxChecked = checkBoxes.any { it.isChecked }

            if (anyPrefsTrue or anyCheckBoxChecked) {
                val selectedParts = checkBoxes.filter { it.isChecked }.map { it.text.toString() }
                val inputsymintent = Intent(this, SubParts_input::class.java).apply {
                    putExtra("selected_parts", ArrayList(selectedParts))
                    putExtra("side", 0) // front=0
                    putExtra("gender", "male")
                }
                startActivity(inputsymintent)
            } else {
                // 沒有任何選項被選擇，顯示 AlertDialog
                AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("請點選至少一種部位")
                    .setPositiveButton("確定") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    // 儲存特有的 CheckBox 狀態到 SharedPreferences
    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("chest", findViewById<CheckBox>(R.id.chest).isChecked)
        editor.putBoolean("abdomen", findViewById<CheckBox>(R.id.abdomen).isChecked)
        editor.putBoolean("lower_abdomen", findViewById<CheckBox>(R.id.lower_abdomen).isChecked)

        // 保存共用的 CheckBox 狀態
        editor.putBoolean("head", findViewById<CheckBox>(R.id.head_1).isChecked)
        editor.putBoolean("neck", findViewById<CheckBox>(R.id.neck_1).isChecked)
        editor.putBoolean("legs", findViewById<CheckBox>(R.id.legs_1).isChecked)
        editor.putBoolean("feet", findViewById<CheckBox>(R.id.feet_1).isChecked)
        editor.putBoolean("whole_body", findViewById<CheckBox>(R.id.whole_body_1).isChecked)
        editor.putBoolean("hands", findViewById<CheckBox>(R.id.hand_1).isChecked)
        editor.putBoolean("skin", findViewById<CheckBox>(R.id.skin_1).isChecked)
        editor.putBoolean("psychology", findViewById<CheckBox>(R.id.psychology_1).isChecked)
        editor.apply()
    }

    // 從 SharedPreferences 讀取特有的 CheckBox 狀態
    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        findViewById<CheckBox>(R.id.chest).isChecked = prefs.getBoolean("chest", false)
        findViewById<CheckBox>(R.id.abdomen).isChecked = prefs.getBoolean("abdomen", false)
        findViewById<CheckBox>(R.id.lower_abdomen).isChecked = prefs.getBoolean("lower_abdomen", false)

        // 恢復共用的 CheckBox 狀態
        findViewById<CheckBox>(R.id.head_1).isChecked = prefs.getBoolean("head", false)
        findViewById<CheckBox>(R.id.neck_1).isChecked = prefs.getBoolean("neck", false)
        findViewById<CheckBox>(R.id.legs_1).isChecked = prefs.getBoolean("legs", false)
        findViewById<CheckBox>(R.id.feet_1).isChecked = prefs.getBoolean("feet", false)
        findViewById<CheckBox>(R.id.whole_body_1).isChecked = prefs.getBoolean("whole_body", false)
        findViewById<CheckBox>(R.id.hand_1).isChecked = prefs.getBoolean("hands", false)
        findViewById<CheckBox>(R.id.skin_1).isChecked = prefs.getBoolean("skin", false)
        findViewById<CheckBox>(R.id.psychology_1).isChecked = prefs.getBoolean("psychology", false)
    }
}