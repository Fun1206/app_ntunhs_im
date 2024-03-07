package com.example.guess_number

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*


class MainActivity : AppCompatActivity() {
    val TAG:String = MainActivity::class.java.simpleName
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler = Handler(Looper.getMainLooper())
        val textView1 = findViewById<TextView>(R.id.textView1)//
        val edit_num = findViewById<EditText>(R.id.edit_num)
        val button1 = findViewById<Button>(R.id.button1)//guess
        val button2 = findViewById<Button>(R.id.button2)//reset
        val textView2 = findViewById<TextView>(R.id.textView2)//區間

        var validate_num:Int
        var min:Int=1
        var max:Int=100

        var secert: Int = Random().nextInt(100)+1//隨機1-100取一數

        /*handler.postDelayed({
            Toast.makeText(this,"5秒後的操作執行了",Toast.LENGTH_SHORT).show()
        },5000)
        */

        /*button1.setOnClickListener{
            textView1.text=name.text
        }*/
        edit_num.text.clear()
        //textView1.text=secert.toString()
        textView2.text=min.toString()+"-"+max.toString()
        button1.setOnClickListener{
            validate_num=edit_num.text.toString().toInt()-secert

            if(validate_num>0)
            {
                textView1.text="請猜小一點 !!!"
                max=edit_num.text.toString().toInt()
                textView2.text=min.toString()+"-"+max.toString()
            }
            else if(validate_num<0)
            {
                textView1.text="請猜大一點 !!!"
                min=edit_num.text.toString().toInt()
                textView2.text=min.toString()+"-"+max.toString()
            }
            else
            {
                textView1.text="恭喜猜對了 !!!"
                Toast.makeText(this,"~ 6秒後重置遊戲 ~",Toast.LENGTH_SHORT).show()
                handler.postDelayed({
                    edit_num.text.clear()
                    min=1
                    max=100
                    secert = Random().nextInt(100)+1//隨機1-100取一數
                    //textView1.text=secert.toString()
                    textView2.text=min.toString()+"-"+max.toString()
                },6000)
            }

        }
        button2.setOnClickListener{
            edit_num.text.clear()
            min=1
            max=100
            secert = Random().nextInt(100)+1//隨機1-100取一數
            //textView1.text=secert.toString()
            textView2.text=min.toString()+"-"+max.toString()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}