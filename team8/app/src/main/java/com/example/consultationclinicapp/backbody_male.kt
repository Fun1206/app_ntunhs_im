package com.example.consultationclinicapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton

class backbody_male : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backbody_male)

        val home = findViewById<ImageButton>(R.id.home_btn)
        val front_btn = findViewById<Button>(R.id.front_btn)
        val back_btn = findViewById<Button>(R.id.back_btn)
        val pervious = findViewById<Button>(R.id.previous_btn)
        val next = findViewById<Button>(R.id.next2_btn)

        // 初始化CheckBoxes
        val checkBoxes = listOf<CheckBox>(
            findViewById(R.id.head_2),
            findViewById(R.id.neck_2),
            findViewById(R.id.back),
            findViewById(R.id.waist),
            findViewById(R.id.Buttocks),
            findViewById(R.id.legs_2),
            findViewById(R.id.feet_2),
            findViewById(R.id.whole_body_2),
            findViewById(R.id.hand_2),
            findViewById(R.id.skin_2),
            findViewById(R.id.psychology_2)
        )

        home.setOnClickListener {
            var homeintent = Intent(this,MainActivity::class.java)
            startActivity(homeintent)
        }
        front_btn.setOnClickListener {
            var frontmaleintent = Intent(this,frontbody_male::class.java)
            startActivity(frontmaleintent)
        }

        /*back_btn.setOnClickListener {
            var backmaleintent = Intent(this,backbody_male::class.java)
            startActivity(backmaleintent)
        }*/

        pervious.setOnClickListener {
            var inputintent = Intent(this,analyze_input::class.java)
            startActivity(inputintent)
        }
        next.setOnClickListener {
            val selectedParts = checkBoxes.filter { it.isChecked }.map { it.text.toString() }
            val inputsymintent = Intent(this, SubParts_input::class.java).apply {
                putExtra("selected_parts", ArrayList(selectedParts))
                putExtra("side", 1) // back=1
                putExtra("gender", "male")
            }
            startActivity(inputsymintent)
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