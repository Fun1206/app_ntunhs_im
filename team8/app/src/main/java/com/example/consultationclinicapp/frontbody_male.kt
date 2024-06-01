package com.example.consultationclinicapp

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
            findViewById(R.id.neak_1),
            findViewById(R.id.chest),
            findViewById(R.id.abdomen),
            findViewById(R.id.lower_abdomen),
            findViewById(R.id.legs_1),
            findViewById(R.id.fooots_1),
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

        /*back_btn.setOnClickListener {
            var backmaleintent = Intent(this,backbody_male::class.java)
            startActivity(backmaleintent)
        }*/

        // 當按下 back_btn，傳遞共用的 CheckBox 狀態到 backbody_male
        back_btn.setOnClickListener {
            val intent = Intent(this, backbody_male::class.java)
            checkBoxes.forEach { checkBox ->
                intent.putExtra(checkBox.resources.getResourceEntryName(checkBox.id), checkBox.isChecked)
            }
            startActivity(intent)
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
        editor.putBoolean("chest", findViewById<CheckBox>(R.id.chest).isChecked)
        editor.putBoolean("abdomen", findViewById<CheckBox>(R.id.abdomen).isChecked)
        editor.putBoolean("lower_abdomen", findViewById<CheckBox>(R.id.lower_abdomen).isChecked)
        editor.apply()
    }

    // 從 SharedPreferences 讀取特有的 CheckBox 狀態
    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        findViewById<CheckBox>(R.id.chest).isChecked = prefs.getBoolean("chest", false)
        findViewById<CheckBox>(R.id.abdomen).isChecked = prefs.getBoolean("abdomen", false)
        findViewById<CheckBox>(R.id.lower_abdomen).isChecked = prefs.getBoolean("lower_abdomen", false)
    }
}