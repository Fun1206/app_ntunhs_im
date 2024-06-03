package com.example.consultationclinicapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
    private val PREFS_NAME = "language_prefs"
    private val KEY_LANGUAGE = "language_key"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subparts_input)
        dbHelper = SQLiteOpenHelper(this)

        val home = findViewById<ImageButton>(R.id.home_btn)
        val previous = findViewById<Button>(R.id.previous3_btn)
        val next = findViewById<Button>(R.id.next3_btn)
        val titleTextView = findViewById<TextView>(R.id.title_textView)
        val selectTextView = findViewById<TextView>(R.id.textView2)
        val container = findViewById<LinearLayout>(R.id.checkboxContainer)
        container.removeAllViews()

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isEnglish = sharedPreferences.getBoolean(KEY_LANGUAGE, false)

        // 根據值更新UI
        updateUI(isEnglish, titleTextView, selectTextView, previous, next)

        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val gender = intent.getStringExtra("gender")

        // 獲取從前一個Activity傳遞過來的資料
        val bodyPartIDList = intent.getStringArrayListExtra("BodyPartID")
        addSelectedParts(prefs, gender, bodyPartIDList)

        bodyPartIDList?.forEach { bodyPartID ->
            val resultList = dbHelper.getBodyPartsByBodyPartID(bodyPartID.toInt())
            if (resultList.isNotEmpty()) {
                val partName = resultList[0].PartName
                dbHelper.getSubPartsByPartId(bodyPartID.toInt())?.let { subParts ->
                    displayOptions(subParts, resultList, container, isEnglish)
                }
            }
        }

        setupListeners(home, previous, gender, next, container, isEnglish)
    }

    private fun addSelectedParts(prefs: SharedPreferences, gender: String?, bodyPartIDList: ArrayList<String>?) {
        // 對 bodyPartIDList 進行擴充
        val partsMap = if (gender == "male") {
            mapOf("back" to "12", "waist" to "13", "buttocks" to "14",
                "chest" to "3", "abdomen" to "4", "lower_abdomen" to "5")
        } else {
            mapOf("back" to "34", "waist" to "35", "buttocks" to "36",
                "chest" to "25", "abdomen" to "26", "lower_abdomen" to "27")
        }

        partsMap.forEach { (key, value) ->
            if (prefs.getBoolean(key, false)) {
                bodyPartIDList?.add(value)
            }
        }
    }

    private fun setupListeners(home: ImageButton, previous: Button, gender: String?, next: Button, container: LinearLayout ,isEnglish: Boolean) {
        home.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        previous.setOnClickListener {
            startActivity(Intent(this, if (gender == "male") frontbody_male::class.java else frontbody_female::class.java))
        }
        next.setOnClickListener {
            // 從LinearLayout容器中檢索所有已勾選的CheckBox的標籤 (即SubPartID)
            val selectedSubPartIDs = mutableListOf<Int>()
            val selectedSubParts = mutableListOf<String>()
            for (i in 0 until container.childCount) {
                val view = container.getChildAt(i)
                if (view is CheckBox && view.isChecked) {
                    selectedSubPartIDs.add(view.tag as Int)  // 收集標籤中的唯一ID
                    selectedSubParts.add(view.text.toString())  // 收集文本
                }
            }

            if (selectedSubPartIDs.isEmpty()) {
                // 沒有選中任何CheckBox，顯示AlertDialog
                AlertDialog.Builder(this)
                    .setTitle(if (isEnglish) "Alert" else "提示")
                    .setMessage(if (isEnglish) "Please select at least one part" else "請點選至少一種細節部位")
                    .setPositiveButton(if (isEnglish) "OK" else "確定") { dialog, _ -> dialog.dismiss() }
                    .show()
            } else {
                // 有選中的CheckBox，進行頁面跳轉
                val intent = Intent(this, Symptoms_input::class.java)
                intent.putStringArrayListExtra("selectedSubParts", ArrayList(selectedSubParts))
                intent.putIntegerArrayListExtra("selectedSubPartIDs", ArrayList(selectedSubPartIDs))
                startActivity(intent)
            }
        }
    }

    private fun displayOptions(SubParts: List<SubPart>, BodyParts: List<BodyPart>, container: LinearLayout, isEnglish: Boolean) {
        val textView = TextView(this).apply {
            text = if (isEnglish) BodyParts[0].En_PartName else BodyParts[0].PartName
            textSize = 24f
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                setMargins(0, 16.toPx(), 0, 16.toPx())
            }
        }
        container.addView(textView)

        SubParts.forEach { part ->
            container.addView(CheckBox(this).apply {
                text = if (isEnglish) part.En_SubPartName else part.SubPartName
                textSize = 20f
                setTypeface(null, Typeface.BOLD)
                setBackgroundResource(R.drawable.checkbox_background)
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    setMargins(0, 8.toPx(), 0, 8.toPx())
                }
                // 設置標籤來儲存SubPartID
                setTag(part.SubPartID)
            })
        }
    }
    private fun updateUI(
        isEnglish: Boolean,
        titleTextView: TextView,
        selectTextView: TextView,
        previousBtn: Button,
        nextBtn: Button
    ) {
        if (isEnglish) {
            titleTextView.text = "Symptom Analysis"
            selectTextView.text = "Select detailed parts (multiple choice)"
            previousBtn.text = "Previous"
            nextBtn.text = "Next"
        } else {
            titleTextView.text = "症狀分析"
            selectTextView.text = "選擇細節部位（多選）"
            previousBtn.text = "上一步"
            nextBtn.text = "下一步"
        }
    }
    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
