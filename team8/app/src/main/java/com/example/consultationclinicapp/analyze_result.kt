package com.example.consultationclinicapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView

class analyze_result : AppCompatActivity() {
    private lateinit var dbHelper: SQLiteOpenHelper
    private val PREFS_NAME = "language_prefs"
    private val KEY_LANGUAGE = "language_key"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyze_result)
        dbHelper = SQLiteOpenHelper(this)
        val home = findViewById<ImageButton>(R.id.home_btn)
        val previous = findViewById<Button>(R.id.previous5_btn)
        val googlemap = findViewById<Button>(R.id.googlemap_btn)
        val titleTextView = findViewById<TextView>(R.id.title_textView)
        val selectTextView = findViewById<TextView>(R.id.textView4)
        // 從Intent中接收症狀ID列表
        val selectedSymptomIDs = intent.getIntegerArrayListExtra("selectedSymptomIDs") ?: arrayListOf()

        val container = findViewById<LinearLayout>(R.id.textviewContainer)
        container.removeAllViews()
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isEnglish = sharedPreferences.getBoolean(KEY_LANGUAGE, false)
        // 根據值更新UI
        updateUI(isEnglish, titleTextView, selectTextView, previous, googlemap)

        // 處理接收到的症狀ID
        if (selectedSymptomIDs.isNotEmpty()) {
            // 如果列表不為空，為每個ID查詢對應的科別資訊
            selectedSymptomIDs.forEach { symptomId ->
                val departments = dbHelper.getDepartmentsBySymptom(symptomId)
                // 這裡可以對查詢到的科別資訊進行顯示或其他處理
                displayDepartments(departments, container, isEnglish)
            }
        }

        home.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }
        //previous.setOnClickListener {
        //    var symintent = Intent(this,Symptoms_input::class.java)
        //    startActivity(symintent)
        //}
        googlemap.setOnClickListener {
            var googlemapintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps"))
            startActivity(googlemapintent)
        }
    }
    private fun displayDepartments(departments: List<Department>, container: LinearLayout, isEnglish: Boolean) {
        val context = container.context
        departments.forEach { department ->
            val cardView = CardView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, context.resources.displayMetrics).toInt()
                    setMargins(margin, margin, margin, margin)
                }

                val linearLayout = LinearLayout(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    orientation = LinearLayout.VERTICAL

                    val titleTextView = TextView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        setBackgroundColor(Color.parseColor("#214A7B")) // dark blue
                        setPadding(8, 8, 8, 8)
                        text = if (isEnglish) department.En_DpmName else department.DpmName
                        setTextColor(Color.WHITE)
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                        setTypeface(typeface, Typeface.BOLD)
                    }

                    val contentTextView = TextView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        setPadding(16, 16, 16, 16)
                        text = if (isEnglish) department.En_Description else department.Description
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    }

                    addView(titleTextView)
                    addView(contentTextView)
                }

                addView(linearLayout)
            }

            container.addView(cardView)
        }
    }

    private fun updateUI(
        isEnglish: Boolean,
        titleTextView: TextView,
        selectTextView: TextView,
        previousBtn: Button,
        googleMapBtn: Button
    ) {
        if (isEnglish) {
            titleTextView.text = "Symptom Analysis"
            selectTextView.text = "Analyze results"
            previousBtn.text = "Previous"
            googleMapBtn.text = "Google Map: Link to Google Maps"
        } else {
            titleTextView.text = "症狀分析"
            selectTextView.text = "分析結果"
            previousBtn.text = "上一步"
            googleMapBtn.text = "Google 地圖：鏈接到Google地圖"
        }
    }
}