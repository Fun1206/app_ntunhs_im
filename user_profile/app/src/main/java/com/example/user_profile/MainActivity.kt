package com.example.user_profile

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.time.DayOfWeek
import java.time.Month
import java.time.Year
import java.util.Calendar

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            val acc = findViewById<EditText>(R.id.acc)
            val pwd = findViewById<EditText>(R.id.pwd)
            val name = findViewById<EditText>(R.id.name)
            val mail = findViewById<EditText>(R.id.mail)
            val phone = findViewById<EditText>(R.id.phone)
            val applyDate = findViewById<EditText>(R.id.Birthday)
            val radGrp_gender = findViewById<RadioGroup>(R.id.group_gender)
            val radBtn_male = findViewById<RadioButton>(R.id.male)
            val radBtn_female = findViewById<RadioButton>(R.id.female)
            val spncity = findViewById<Spinner>(R.id.spn_city)
            val adapter = ArrayAdapter.createFromResource(this, R.array.city,android.R.layout.simple_spinner_dropdown_item)
            val listen_to_music = findViewById<CheckBox>(R.id.listen_to_music)
            val draw = findViewById<CheckBox>(R.id.draw)
            val play_basketball = findViewById<CheckBox>(R.id.play_basketball)
            val send = findViewById<Button>(R.id.button)

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

        //日期
        applyDate.setOnClickListener{
            val calendar=Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this,
                { _, year, month, day ->
                    run{
                            var format = "${setDateFormat(year, month, day)}"
                            applyDate.setText(format)
                        }
                }, year, month, day).show()
        }
        send.setOnClickListener{

            val hobby = StringBuilder("\n")

            // 获取选中的RadioButton的ID
            val selectedRadioButtonId = radGrp_gender.checkedRadioButtonId
            // 根据ID找到RadioButton
            val radioButton = findViewById<RadioButton>(selectedRadioButtonId)


            if (listen_to_music.isChecked) hobby.append("聽音樂\n")
            if (draw.isChecked) hobby.append("繪圖\n")
            if (play_basketball.isChecked) hobby.append("打籃球\n")

            AlertDialog.Builder(this).setTitle("送出訊息").setMessage(
                    "帳號: "+acc.text.toString()+"\n" +
                    "密碼: "+pwd.text.toString()+"\n" +
                    "姓名: "+name.text.toString() + "\n" +
                    "電子信箱: "+mail.text.toString() + "\n" +
                    "電話: "+phone.text.toString()+"\n" +
                    "生日: "+applyDate.text.toString() + "\n" +
                    "性別: ${radioButton.text}" + "\n" +
                    "興趣: "+hobby.toString()).create().show()
        }
    }
    private fun setDateFormat(year: Int,month: Int, day: Int): String
    {
        return "$year-${month + 1}-$day"
    }
}

