package com.example.consultationclinicapp

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
        val checkBoxes = listOf(
            findViewById<CheckBox>(R.id.head_1),
            findViewById<CheckBox>(R.id.neak_1),
            findViewById<CheckBox>(R.id.chest),
            findViewById<CheckBox>(R.id.abdomen),
            findViewById<CheckBox>(R.id.lower_abdomen),
            findViewById<CheckBox>(R.id.legs_1),
            findViewById<CheckBox>(R.id.fooots_1),
            findViewById<CheckBox>(R.id.whole_body_1),
            findViewById<CheckBox>(R.id.hand_1),
            findViewById<CheckBox>(R.id.skin_1),
            findViewById<CheckBox>(R.id.psychology_1)
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
        /*next.setOnClickListener {
            val selectedParts = checkBoxes.filter { it.isChecked }.map { it.text.toString() }
            var inputsymintent = Intent(this,symptom_input::class.java)
            inputsymintent.putExtra("pervious_record", "male")
            startActivity(inputsymintent)
        }*/

        next.setOnClickListener {
            val selectedParts = checkBoxes.filter { it.isChecked }.map { it.text.toString() }
            val inputsymintent = Intent(this, symptom_input::class.java).apply {
                putExtra("selected_parts", ArrayList(selectedParts))
                putExtra("front_back", 0) // front=0
                putExtra("pervious_record", "male")
            }
            startActivity(inputsymintent)
        }
    }
}