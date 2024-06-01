package com.example.consultationclinicapp

import android.app.AlertDialog
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

class Symptoms_input : AppCompatActivity() {
    private lateinit var dbHelper: SQLiteOpenHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptoms_input)
        dbHelper = SQLiteOpenHelper(this)
        val home = findViewById<ImageButton>(R.id.home_btn)
        val pervious = findViewById<Button>(R.id.previous4_btn)
        val next = findViewById<Button>(R.id.next4_btn)
        val container = findViewById<LinearLayout>(R.id.checkboxContainer)
        container.removeAllViews()

        val log = findViewById<Button>(R.id.log_btn)/*查看已選擇症狀*/
        // 獲取從上一個Activity傳來的數據
        val selectedSubParts = intent.getStringArrayListExtra("selectedSubParts")
        val selectedSubPartIDs = intent.getIntegerArrayListExtra("selectedSubPartIDs")

        // 構建要顯示的信息
        val displayText = StringBuilder("選擇的部位與其症狀:\n\n")
        selectedSubPartIDs?.forEachIndexed { index, id ->
            val partName = selectedSubParts?.get(index) ?: "未知部位"
            val symptoms = dbHelper.getSymptomsBySubPartId(id)
            displayText.append("部位名稱: $partName (ID: $id)\n症狀:\n${symptoms.joinToString(separator = "\n") { it.SymName }}\n\n")
        }

        // 顯示對話框
        AlertDialog.Builder(this)
            .setTitle("選擇的部位與症狀資訊")
            .setMessage(displayText.toString())
            .setPositiveButton("確定") { dialog, _ -> dialog.dismiss() }
            .show()
        // 動態生成checkBOX
        selectedSubPartIDs?.forEachIndexed { index, id ->
            val SubParts = selectedSubParts?.get(index)
            SubParts?.let { displaySymptoms(dbHelper.getSymptomsBySubPartId(id), it, container) }
        }

        home.setOnClickListener {
            var homeintent = Intent(this,MainActivity::class.java)
            startActivity(homeintent)
        }
        pervious.setOnClickListener {
            var subintent = Intent(this,SubParts_input::class.java)
            startActivity(subintent)
        }
        next.setOnClickListener {
            var resultintent = Intent(this,analyze_result::class.java)
            startActivity(resultintent)
        }
    }
    private fun displaySymptoms(symptoms: List<Symptom>, SubPartName: String, container: LinearLayout) {
        val textView = TextView(this).apply {
            text = SubPartName
            textSize = 24f
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                setMargins(0, 16.toPx(), 0, 16.toPx())
            }
        }
        container.addView(textView)
        symptoms.forEach { symptom ->
            container.addView(CheckBox(this).apply {
                text = symptom.SymName  // 使用症狀的名稱作為CheckBox的文本
                textSize = 20f
                setTypeface(null, Typeface.BOLD)
                setBackgroundResource(R.drawable.checkbox_background)
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    setMargins(0, 8.toPx(), 0, 8.toPx())
                }
                setTag(symptom.SymptomID)  // 將症狀的ID設置為CheckBox的標籤
            })
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}