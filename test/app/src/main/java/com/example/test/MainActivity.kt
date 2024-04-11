package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val phone = findViewById<EditText>(R.id.editPhone)

        val spn_b = findViewById<Spinner>(R.id.spinner_b)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.number_of_b,
            android.R.layout.simple_spinner_item
        )
        spn_b.adapter = adapter

        val spn_s = findViewById<Spinner>(R.id.spinner_s)
        val adapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.number_of_s,
            android.R.layout.simple_spinner_item
        )
        spn_s.adapter = adapter2

        val c_c = findViewById<CheckBox>(R.id.cheak_chair)
        val c_f = findViewById<CheckBox>(R.id.check_fork)

        val send = findViewById<Button>(R.id.send)

        spn_b.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val num_b = resources.getStringArray(R.array.number_of_b)
                if (position > 0)
                    Toast.makeText(
                        this@MainActivity,
                        "您選擇的是:" + num_b[position],
                        Toast.LENGTH_SHORT
                    ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spn_s.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val num_s = resources.getStringArray(R.array.number_of_s)
                if (position > 0)
                    Toast.makeText(
                        this@MainActivity,
                        "您選擇的是:" + num_s[position],
                        Toast.LENGTH_SHORT
                    ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        send.setOnClickListener {
            val phoneNum = phone.text.toString() // 獲取電話號碼
            val selectedB = spn_b.selectedItem.toString() // 獲取spn_b的選擇
            val selectedS = spn_s.selectedItem.toString() // 獲取spn_s的選擇
            var need = ""
            if(c_c.isChecked()){
                need += c_c.getText().toString()
            }
            if(c_f.isChecked()){
                need += c_f.getText().toString()
            }
            val user_message= "訂位電話:"+phoneNum+"\n"+
                              "訂位人數:"+selectedB+"大"+selectedS+"小"+"\n"+
                              "需要"+ need+"\n"

            Log.e("MainActivity", "使用者提交的訊息: $user_message")

            val intent = Intent(this, secondactivity::class.java).apply {
                putExtra("USER_MESSAGE", user_message)
            }
            startActivity(intent)
        }
    }
}