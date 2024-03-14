package com.example.user_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            val spncity = findViewById<Spinner>(R.id.spn_city)
            val adapter = ArrayAdapter.createFromResource(this, R.array.city,android.R.layout.simple_spinner_dropdown_item)
            val radGrp_gender = findViewById<RadioGroup>(R.id.group_gender)
            val radBtn_male = findViewById<RadioButton>(R.id.male)
            val radBtn_female = findViewById<RadioButton>(R.id.female)

        spncity.adapter = adapter
        spncity.onItemSelectedListener = object: AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>,  view:View, pos : Int, id: Long)
            {
                val city = resources.getStringArray(R.array.city)
                if(pos>0)
                    Toast.makeText(this@MainActivity,"你選的是:"+city[pos],Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        radGrp_gender.setOnCheckedChangeListener{ _, checkedId ->
            var gender = when (checkedId)
            {
                R.id.female -> radBtn_female.text.toString()
                R.id.male -> radBtn_male.text.toString()
                else -> "I don't know"
            }
            Toast.makeText(this,gender,Toast.LENGTH_SHORT).show()
        }

    }
}

