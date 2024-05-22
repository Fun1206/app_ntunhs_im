package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class secondactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondactivity)

        // 從Intent獲取傳遞過來的user_message
        val userMessage = intent.getStringExtra("USER_MESSAGE")

        // 使用userMessage（例如顯示在TextView中）
        val user_mes = findViewById<TextView>(R.id.user_mes)
        user_mes.text = userMessage

        val back = findViewById<Button>(R.id.back)
        back.setOnClickListener{
            var mainIntent = Intent(this,MainActivity::class.java)
            startActivity(mainIntent)
        }
    }
}