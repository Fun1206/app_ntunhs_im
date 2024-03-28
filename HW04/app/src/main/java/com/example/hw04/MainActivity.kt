package com.example.hw04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity()
{
    private lateinit var txtcom : TextView
    private lateinit var txtresult : TextView
    private lateinit var btnscissors : ImageButton
    private lateinit var btnrock : ImageButton
    private lateinit var btnpaper : ImageButton
    private lateinit var ImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtcom = findViewById(R.id.txtcom)
        txtresult = findViewById(R.id.result)
        btnscissors = findViewById(R.id.Scissors)
        btnrock = findViewById(R.id.Rock)
        btnpaper = findViewById(R.id.Paper)
        ImageView = findViewById(R.id.P1_image)


        btnrock.setOnClickListener {
            playGame(Choice.rock)
        }
        btnscissors.setOnClickListener {
            playGame(Choice.scissors)
        }
        btnpaper.setOnClickListener {
            playGame(Choice.paper)
        }

    }
        enum class Choice
        {
            rock,scissors,paper
        }
        fun playGame(playerChoice:Choice)
        {
            val choice = Choice.values()
            val computerChoice = choice[Random().nextInt(choice.size)]

            when {
                playerChoice == computerChoice -> {
                    txtcom.setText(getChoiceString(computerChoice))
                    txtresult.setText(R.string.draw)
                    ImageView.setImageResource(getComIamge(computerChoice))
                }
                (playerChoice == Choice.scissors && computerChoice == Choice.paper) ||
                        (playerChoice == Choice.rock && computerChoice == Choice.scissors) ||
                        (playerChoice == Choice.paper && computerChoice == Choice.rock) -> {
                    txtcom.setText(getChoiceString(computerChoice))
                    ImageView.setImageResource(getComIamge(computerChoice))
                    txtresult.setText(R.string.win)
                }
                else -> {
                    txtcom.setText(getChoiceString(computerChoice))
                    txtresult.setText(R.string.lose)
                    ImageView.setImageResource(getComIamge(computerChoice))
                }
            }
        }
        fun getChoiceString(choice:Choice): Int
        {
            return when (choice)
            {
                Choice.scissors -> R.string.scissors
                Choice.rock -> R.string.rock
                Choice.paper -> R.string.paper
            }
        }
        fun getComIamge(choice: Choice): Int {
            return when (choice) {
                Choice.scissors -> R.drawable.scissor
                Choice.rock -> R.drawable.rock
                Choice.paper -> R.drawable.paper
            }
        }

}
