package com.example.consultationclinicapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton

class frontbody_female : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frontbody_female)
        val home = findViewById<ImageButton>(R.id.home_btn)
        val frontf_btn = findViewById<Button>(R.id.frontf_btn)
        val backf_btn = findViewById<Button>(R.id.backf_btn)
        val pervious = findViewById<Button>(R.id.previous_btn)
        val next = findViewById<Button>(R.id.next2_btn)

        // 初始化CheckBoxes
        val checkBoxes = listOf<CheckBox>(
            findViewById(R.id.head_f1),
            findViewById(R.id.neck_f1),
            findViewById(R.id.chest_f),
            findViewById(R.id.abdomen_f),
            findViewById(R.id.lower_abdomen_f),
            findViewById(R.id.legs_f1),
            findViewById(R.id.feet_f1),
            findViewById(R.id.whole_body_f1),
            findViewById(R.id.hand_f1),
            findViewById(R.id.skin_f1),
            findViewById(R.id.psychology_f1)
        )

        home.setOnClickListener {
            var homeintent = Intent(this,MainActivity::class.java)
            startActivity(homeintent)
        }

        /*frontf_btn.setOnClickListener {
            var frontfemaleintent = Intent(this,frontbody_female::class.java)
            startActivity(frontfemaleintent)
        }*/

        backf_btn.setOnClickListener {
            var backfemaleintent = Intent(this,backbody_female::class.java)
            startActivity(backfemaleintent)
        }

        pervious.setOnClickListener {
            var inputintent = Intent(this,analyze_input::class.java)
            startActivity(inputintent)
        }
        next.setOnClickListener {
            val selectedParts = checkBoxes.filter { it.isChecked }.map { it.text.toString() }
            val inputsymintent = Intent(this, SubParts_input::class.java).apply {
                putExtra("selected_parts", ArrayList(selectedParts))
                putExtra("side", 0) // front=0
                putExtra("gender", "female")
            }
            startActivity(inputsymintent)
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