package com.example.guess_number

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.guess_number.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val TAG:String = MainActivity::class.java.simpleName
    private lateinit var handler: Handler
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: GameModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler = Handler(Looper.getMainLooper())
        model = GameModel()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editnum.text.clear()
        binding.button1.setOnClickListener{
            val guess = binding.editnum.text.toString().toInt()
            when {
                model.isGuessTooLow(guess) -> {
                    model.min = guess
                    binding.textView1.text = "你猜得太小囉!"
                    binding.textView2.text = "${model.min}-${model.max}"
                }
                model.isGuessTooHigh(guess) -> {
                    model.max = guess
                    binding.textView1.text = "你猜得太大囉!"
                    binding.textView2.text = "${model.min}-${model.max}"
                }
                else -> {
                    binding.textView1.text = "你猜對了!!"
                    binding.textView2.text = "正確數字是${model.secret}"
                    Toast.makeText(this, "6秒後重置遊戲", Toast.LENGTH_SHORT).show()
                    handler.postDelayed({
                        model.resetGame()
                        binding.textView1.text = "重新開始猜數字"
                        binding.textView2.text = "${model.min}-${model.max}"
                        binding.editnum.text.clear()
                    }, 6000)
                }
            }
        }
        binding.button2.setOnClickListener{
            model.resetGame()
            binding.textView1.text = "重新開始猜數字"
            binding.textView2.text = "${model.min}-${model.max}"
            binding.editnum.text.clear()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}