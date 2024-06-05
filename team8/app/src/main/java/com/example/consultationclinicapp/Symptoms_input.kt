package com.example.consultationclinicapp

import android.app.AlertDialog
import android.content.Context
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
    private val PREFS_NAME = "language_prefs"
    private val KEY_LANGUAGE = "language_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptoms_input)
        dbHelper = SQLiteOpenHelper(this)
        val home = findViewById<ImageButton>(R.id.home_btn)
        val previous = findViewById<Button>(R.id.previous4_btn)
        val next = findViewById<Button>(R.id.next4_btn)
        val titleTextView = findViewById<TextView>(R.id.title_textView)
        val selectTextView = findViewById<TextView>(R.id.textView3)
        val container = findViewById<LinearLayout>(R.id.checkboxContainer)
        container.removeAllViews()

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isEnglish = sharedPreferences.getBoolean(KEY_LANGUAGE, false)
        val log = findViewById<Button>(R.id.log_btn)/*查看已選擇症狀*/

        // 根據值更新UI
        updateUI(isEnglish, titleTextView, selectTextView, previous, next, log)



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
        // showAlert("選擇的部位與症狀資訊", displayText.toString(), isEnglish)

        // 動態生成checkBOX
        selectedSubPartIDs?.forEachIndexed { index, id ->
            val SubParts = selectedSubParts?.get(index)
            SubParts?.let { displaySymptoms(dbHelper.getSymptomsBySubPartId(id), it, container, isEnglish) }
        }

        setupButtons(home, previous, next, log, container, isEnglish)
    }

    private fun setupButtons(home: ImageButton, previous: Button, next: Button, log: Button, container: LinearLayout, isEnglish: Boolean) {
        log.setOnClickListener {
            val selectedSymptoms = container.children.filterIsInstance<CheckBox>()
                .filter { it.isChecked }
                .joinToString(separator = "\n") {
                    "${it.text}"
                }

            if (selectedSymptoms.isNotEmpty()) {
                showAlert(if (isEnglish) "Selected symptoms" else "已選擇的症狀", selectedSymptoms, isEnglish)
            } else {
                showAlert(if (isEnglish) "Alert" else "提示", if (isEnglish) "No symptoms selected" else "沒有選擇任何症狀", isEnglish)
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
                showAlert(if (isEnglish) "Alert" else "提示", if (isEnglish) "Please select at least one symptom" else "請選擇至少一種症狀", isEnglish)
            }
        }
    }

    private fun showAlert(title: String, message: String, isEnglish: Boolean) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(if (isEnglish) "OK" else "確定") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun displaySymptoms(symptoms: List<Symptom>, SubPartName: String, container: LinearLayout, isEnglish: Boolean) {
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
                text = if (isEnglish) "No symptoms found" else "查無症狀"
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
                    text = if (isEnglish) symptom.En_SymName else symptom.SymName // 使用症狀的名稱作為CheckBox的文本
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

    private fun updateUI(
        isEnglish: Boolean,
        titleTextView: TextView,
        selectTextView: TextView,
        previousBtn: Button,
        nextBtn: Button,
        logBtn: Button
    ) {
        if (isEnglish) {
            titleTextView.text = "Symptom Analysis"
            titleTextView.textSize = 30f
            selectTextView.text = "Select symptoms\n(multiple choice)"
            selectTextView.textSize = 20f
            previousBtn.text = "Previous"
            nextBtn.text = "Next"
            logBtn.text = "View selected symptoms"
        } else {
            titleTextView.text = "症狀分析"
            titleTextView.textSize = 34f
            selectTextView.text = "選擇症狀（多選）"
            selectTextView.textSize = 24f
            previousBtn.text = "上一步"
            nextBtn.text = "下一步"
            logBtn.text = "查看已選擇症狀"
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}