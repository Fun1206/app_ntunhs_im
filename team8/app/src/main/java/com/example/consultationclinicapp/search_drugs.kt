package com.example.consultationclinicapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

class search_drugs : AppCompatActivity() {
    private lateinit var dbHelper: SQLiteOpenHelper
    private val PREFS_NAME = "language_prefs"
    private val KEY_LANGUAGE = "language_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_drugs)
        dbHelper = SQLiteOpenHelper(this)

        val home = findViewById<ImageButton>(R.id.home_btn)
        val search = findViewById<ImageButton>(R.id.search_btn)
        val keyword = findViewById<EditText>(R.id.search_field)
        val container = findViewById<LinearLayout>(R.id.results_container)
        val titleTextView = findViewById<TextView>(R.id.title_textView)
        container.removeAllViews()
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isEnglish = sharedPreferences.getBoolean(KEY_LANGUAGE, false)

        // 根據值更新UI
        updateUI(isEnglish, titleTextView, keyword)

        home.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }

        search.setOnClickListener {
            container.removeAllViews()
            val keywordText = keyword.text.toString()
            if (keywordText.isNotEmpty()) {
                val medicines = dbHelper.searchMedicines(keywordText)
                // 這裡可以對查詢到的藥物進行動態顯示BTN選項
                displayMedicines(medicines, container, isEnglish)
            }
        }
    }

    private fun displayMedicines(medicines: List<Medicine>, container: LinearLayout, isEnglish: Boolean) {
        val context = container.context

        // 創建並配置 TextView 顯示標題
        val headerTextView = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = if (isEnglish) "Results are shown below:" else "查詢結果顯示如下："
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)
        }
        container.addView(headerTextView) // 添加標題到容器

        // 為每個藥品生成一個按鈕
        medicines.forEach { medicine ->
            val button = Button(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, context.resources.displayMetrics).toInt()
                    bottomMargin = topMargin
                }
                text = if (isEnglish) medicine.En_MedicineName else medicine.MedicineName
                background = context.getDrawable(R.drawable.search_btn_background)
                setTextColor(Color.BLACK)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)

                setOnClickListener {
                    // 創建Intent，並將藥品資訊傳送至search_result Activity
                    val bundle = Bundle().apply {
                        putInt("MedicineID", medicine.MedicineID)
                        putString("MedicineName", medicine.MedicineName)
                        putString("En_MedicineName", medicine.En_MedicineName)
                        putString("Uses", medicine.Uses)
                        putString("En_Uses", medicine.En_Uses)
                    }
                    val intent = Intent(context, search_result::class.java).apply {
                        putExtras(bundle)
                    }
                    context.startActivity(intent)
                }
            }
            container.addView(button) // 添加按鈕到容器
        }
    }

    private fun updateUI(
        isEnglish: Boolean,
        titleTextView: TextView,
        keyword: EditText
    ) {
        if (isEnglish) {
            titleTextView.text = "Drug Inquiry"
            keyword.hint = "Enter drug name"
        } else {
            titleTextView.text = "藥物查詢"
            keyword.hint = "輸入藥物名稱"
        }
    }
}