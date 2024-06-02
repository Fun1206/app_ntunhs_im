package com.example.consultationclinicapp

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyze_result)
        dbHelper = SQLiteOpenHelper(this)
        val home = findViewById<ImageButton>(R.id.home_btn)
        val previous = findViewById<Button>(R.id.previous5_btn)
        val googlemap = findViewById<Button>(R.id.googlemap_btn)
        // 從Intent中接收症狀ID列表
        val selectedSymptomIDs = intent.getIntegerArrayListExtra("selectedSymptomIDs") ?: arrayListOf()

        val container = findViewById<LinearLayout>(R.id.textviewContainer)
        container.removeAllViews()

        // 處理接收到的症狀ID
        if (selectedSymptomIDs.isNotEmpty()) {
            // 如果列表不為空，為每個ID查詢對應的科別資訊
            selectedSymptomIDs.forEach { symptomId ->
                val departments = dbHelper.getDepartmentsBySymptom(symptomId)
                // 這裡可以對查詢到的科別資訊進行顯示或其他處理
                displayDepartments(departments, container)
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
    private fun displayDepartments(departments: List<Department>, container: LinearLayout) {
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
                        //text = "${department.DpmName} (${department.En_DpmName})"
                        text = "${department.DpmName}"
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
                        // text = "${department.Description} / ${department.En_Description}"
                        text = "${department.Description}"
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
}