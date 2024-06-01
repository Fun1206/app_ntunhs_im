package com.example.consultationclinicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton

class backbody_female : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backbody_female)

        val home = findViewById<ImageButton>(R.id.home_btn)
        val frontf_btn = findViewById<Button>(R.id.frontf_btn)
        val backf_btn = findViewById<Button>(R.id.backf_btn)
        val pervious = findViewById<Button>(R.id.previous_btn)
        val next = findViewById<Button>(R.id.next2_btn)

        // 初始化CheckBoxes
        val checkBoxes = listOf<CheckBox>(
            findViewById(R.id.head_f2),
            findViewById(R.id.neak_f2),
            findViewById(R.id.back_f),
            findViewById(R.id.waist_f),
            findViewById(R.id.Buttocks_f),
            findViewById(R.id.legs_f2),
            findViewById(R.id.fooots_f2),
            findViewById(R.id.whole_body_f2),
            findViewById(R.id.hand_f2),
            findViewById(R.id.skin_f2),
            findViewById(R.id.psychology_f2)
        )

        home.setOnClickListener {
            var homeintent = Intent(this,MainActivity::class.java)
            startActivity(homeintent)
        }
        frontf_btn.setOnClickListener {
            var frontfemaleintent = Intent(this,frontbody_female::class.java)
            startActivity(frontfemaleintent)
        }

        /*backf_btn.setOnClickListener {
            var backfemaleintent = Intent(this,backbody_female::class.java)
            startActivity(backfemaleintent)
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
                putExtra("gender", "female")
            }
            startActivity(inputsymintent)
        }

    }
}