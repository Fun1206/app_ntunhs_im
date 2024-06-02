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
import androidx.core.view.children

class Symptoms_input : AppCompatActivity() {
    private lateinit var dbHelper: SQLiteOpenHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptoms_input)
        dbHelper = SQLiteOpenHelper(this)
        val home = findViewById<ImageButton>(R.id.home_btn)
        val previous = findViewById<Button>(R.id.previous4_btn)
        val next = findViewById<Button>(R.id.next4_btn)
        val container = findViewById<LinearLayout>(R.id.checkboxContainer)
        container.removeAllViews()

        val log = findViewById<Button>(R.id.log_btn)/*查看已選擇症狀*/
        log.setOnClickListener {
            val checkBoxList = container.children.filterIsInstance<CheckBox>()
            val selectedSymptoms = checkBoxList.filter { it.isChecked }.joinToString(separator = "\n") {
                "${it.text}, SymptomID:${it.tag}"  // 現在會顯示格式為 "id:?, 症狀名稱"
            }

            if (selectedSymptoms.isNotEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("已選擇的症狀")
                    .setMessage(selectedSymptoms)
                    .setPositiveButton("確定") { dialog, _ -> dialog.dismiss() }
                    .show()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("沒有選擇任何症狀")
                    .setPositiveButton("確定") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        }
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
        showAlert("選擇的部位與症狀資訊", displayText.toString())

        // 動態生成checkBOX
        selectedSubPartIDs?.forEachIndexed { index, id ->
            val SubParts = selectedSubParts?.get(index)
            SubParts?.let { displaySymptoms(dbHelper.getSymptomsBySubPartId(id), it, container) }
        }
        setupButtons(home, previous, next, log, container)
    }

    private fun setupButtons(home: ImageButton, previous: Button, next: Button, log: Button, container: LinearLayout) {
        log.setOnClickListener {
            val selectedSymptoms = container.children.filterIsInstance<CheckBox>()
                .filter { it.isChecked }
                .joinToString(separator = "\n") {
                    "${it.text}, SymptomID:${it.tag}"
                }

            if (selectedSymptoms.isNotEmpty()) {
                showAlert("已選擇的症狀", selectedSymptoms)
            } else {
                showAlert("提示", "沒有選擇任何症狀")
            }
        }

        home.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        //previous.setOnClickListener {
        //    startActivity(Intent(this, if (intent.getStringExtra("gender") == "male") frontbody_male::class.java else frontbody_female::class.java))
        //}

        next.setOnClickListener {
            val checkedTags = mutableListOf<Int>()
            container.children.filterIsInstance<CheckBox>().forEach { checkBox ->
                if (checkBox.isChecked) {
                    checkedTags.add(checkBox.tag as Int)
                }
            }

            if (checkedTags.isNotEmpty()) {
                val nextIntent = Intent(this, analyze_result::class.java)
                nextIntent.putIntegerArrayListExtra("selectedSymptomIDs", ArrayList(checkedTags))
                startActivity(nextIntent)
            } else {
                showAlert("提示", "請選擇至少一種症狀")
            }
        }
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("確定") { dialog, _ -> dialog.dismiss() }
            .show()
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
        if (symptoms.isEmpty()) {
            // 如果沒有症狀，則添加一個TextView顯示"查無症狀"
            val noSymptomsText = TextView(this).apply {
                text = "查無症狀"
                textSize = 20f
                setTypeface(null, Typeface.ITALIC)
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    setMargins(0, 8.toPx(), 0, 8.toPx())
                }
            }
            container.addView(noSymptomsText)
        } else {
            symptoms.forEach { symptom ->
                container.addView(CheckBox(this).apply {
                    text = symptom.SymName  // 使用症狀的名稱作為CheckBox的文本
                    textSize = 20f
                    setTypeface(null, Typeface.BOLD)
                    setBackgroundResource(R.drawable.checkbox_background)
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                        setMargins(0, 8.toPx(), 0, 8.toPx())
                    }
                    tag = symptom.SymptomID  // 將症狀的ID設為CheckBox的標籤
                })
            }
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}