package com.example.consultationclinicapp

import android.content.Intent
import android.content.res.Resources
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

class SubParts_input : AppCompatActivity() {
    private lateinit var dbHelper: SQLiteOpenHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subparts_input)
        dbHelper = SQLiteOpenHelper(this)
        val home = findViewById<ImageButton>(R.id.home_btn)
        val pervious = findViewById<Button>(R.id.previous3_btn)
        val next = findViewById<Button>(R.id.next3_btn)

        val container = findViewById<LinearLayout>(R.id.checkboxContainer)
        container.removeAllViews()

        // 接收傳遞的數據
        val gender = intent.getStringExtra("gender")

        val selectedParts = intent.getStringArrayListExtra("selected_parts")
        val side = intent.getIntExtra("side", -1)

        if (selectedParts != null && selectedParts.size >= 1 && side != -1) {
            for (part in selectedParts) {
                val BodyPartID = dbHelper.getBodyPartIDByPartNameAndPosition(part, side)
                val subParts = BodyPartID?.let { it1 ->
                    dbHelper.getSubPartsByPartId(it1)
                }
                if (subParts != null) {
                    displayOptions(subParts, part, container)
                }
            }
        }
        home.setOnClickListener {
            var homeintent = Intent(this,MainActivity::class.java)
            startActivity(homeintent)
        }
        pervious.setOnClickListener {
            val bodyintent = if (gender == "male") {
                Intent(this,frontbody_male::class.java)
            }else{
                Intent(this,frontbody_female::class.java)
            }
            startActivity(bodyintent)
        }
        next.setOnClickListener {
            var homeintent = Intent(this,MainActivity::class.java)
            startActivity(homeintent)
            var Symintent = Intent(this,Symptoms_input::class.java)
            startActivity(Symintent)
        }
    }

    fun displayOptions(bodyParts: List<SubPart>, partName: String, container: LinearLayout) {
        // 創建並設置 TextView
        val textView = TextView(this)
        textView.text = partName  // 設置顯示的部位
        textView.textSize = 24f  // 設置文字大小
        textView.setTypeface(null, Typeface.BOLD)  // 設置文字樣式為粗體
        textView.gravity = Gravity.CENTER_HORIZONTAL  // 文字居中顯示
        val textLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        textLayoutParams.setMargins(0, 16.toPx(), 0, 16.toPx())  // 設置上下邊距
        textView.layoutParams = textLayoutParams
        container.addView(textView)  // 添加 TextView 到容器

        for (part in bodyParts) {
            val checkBox = CheckBox(this)
            checkBox.text = part.SubPartName
            checkBox.textSize = 20f // 設置文字大小
            checkBox.setTypeface(null, Typeface.BOLD) // 設置文字樣式為粗體

            // 設置背景，需要在 drawable 資源目錄中有 checkbox_background.xml
            checkBox.setBackgroundResource(R.drawable.checkbox_background)

            // 創建 LayoutParams 以設置邊距
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 8.toPx(), 0, 8.toPx()) // 設置上下邊距，轉換 dp 為 px
            checkBox.layoutParams = layoutParams

            container.addView(checkBox)
        }
    }

    // Helper function to convert dp to pixel
    fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}