package com.example.consultationclinicapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog


class analyze_input : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyze_input)

        val home = findViewById<ImageButton>(R.id.home_btn)
        val gender = findViewById<RadioGroup>(R.id.gender)
        val male = findViewById<RadioButton>(R.id.male)
        val female = findViewById<RadioButton>(R.id.female)

        val spn_age =findViewById<Spinner>(R.id.age_spn)
        val adapter = ArrayAdapter.createFromResource(this,R.array.age,R.layout.spinner_item)

        adapter.setDropDownViewResource(R.layout.spinner_item)
        spn_age.adapter = adapter

        val next = findViewById<Button>(R.id.next_btn)

            home.setOnClickListener {
            var homeintent = Intent(this,MainActivity::class.java)
            startActivity(homeintent)
            }

            next.setOnClickListener {

                val selectedGenderId = gender.checkedRadioButtonId

                if (selectedGenderId == -1) {
                    // 顯示提示框
                    AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("請選擇性別選項")
                        .setPositiveButton("確定", null)
                        .show()
                } else {
                    // 根據選中的RadioButton跳轉到不同的Activity
                    val bodyintent = if (male.isChecked) {
                        Intent(this, MainActivity2::class.java)
                    } else {
                        Intent(this, frontbody_female::class.java)
                    }
                    startActivity(bodyintent)
                }

            }

        // 設置 Spinner 的選項選擇監聽器
        spn_age.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                // 獲取選擇的項目
                val age = resources.getStringArray(R.array.age)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }


        }
    }
}

