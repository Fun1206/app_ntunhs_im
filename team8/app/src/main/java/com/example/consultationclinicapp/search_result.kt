package com.example.consultationclinicapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class search_result : AppCompatActivity() {
    private val PREFS_NAME = "language_prefs"
    private val KEY_LANGUAGE = "language_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val MedicineID = intent.extras?.getInt("MedicineID")
        val MedicineName = intent.extras?.getString("MedicineName")
        val En_MedicineName = intent.extras?.getString("En_MedicineName")
        val Uses = intent.extras?.getString("Uses")
        val En_Uses = intent.extras?.getString("En_Uses")

        val titleTextView = findViewById<TextView>(R.id.title_textView)
        val drug_name = findViewById<TextView>(R.id.drug_name)
        val scientific_name = findViewById<TextView>(R.id.scientific_name)
        val drug_image = findViewById<ImageView>(R.id.drug_image)
        val contentTextView = findViewById<TextView>(R.id.contentTextView)
        val descriptionTitleTextView = findViewById<TextView>(R.id.titleTextView)
        val home = findViewById<ImageButton>(R.id.home_btn)
        val search_again = findViewById<Button>(R.id.search_again_btn)

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isEnglish = sharedPreferences.getBoolean(KEY_LANGUAGE, false)

        // 根據值更新UI
        updateUI(isEnglish, titleTextView, drug_name, scientific_name, contentTextView, descriptionTitleTextView, search_again, MedicineName, En_MedicineName, Uses, En_Uses)

        val resourceId = getResourceId(this, "drug${MedicineID}", "drawable")
        if (resourceId != 0) drug_image.setImageResource(resourceId)

        home.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }
        search_again.setOnClickListener {
            val homeIntent = Intent(this, search_drugs::class.java)
            startActivity(homeIntent)
        }
    }

    private fun updateUI(
        isEnglish: Boolean,
        titleTextView: TextView,
        drugName: TextView,
        scientificName: TextView,
        contentTextView: TextView,
        descriptionTitleTextView: TextView,
        searchAgainBtn: Button,
        medicineName: String?,
        enMedicineName: String?,
        uses: String?,
        enUses: String?
    ) {
        if (isEnglish) {
            titleTextView.text = "Medication Details"
            drugName.text = enMedicineName
            scientificName.text = medicineName
            contentTextView.text = enUses
            descriptionTitleTextView.text = "Medication Description"
            searchAgainBtn.text = "Continue Inquiry"
        } else {
            titleTextView.text = "藥物詳細資料"
            drugName.text = medicineName
            scientificName.text = enMedicineName
            contentTextView.text = uses
            descriptionTitleTextView.text = "藥物描述"
            searchAgainBtn.text = "繼續查詢"
        }
    }

    // 用於從資源ID獲得實際的drawable ID
    fun getResourceId(context: Context, name: String, defType: String): Int {
        return context.resources.getIdentifier(name, defType, context.packageName)
    }
}