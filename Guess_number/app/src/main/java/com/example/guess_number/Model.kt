package com.example.guess_number

import kotlin.random.Random

class GameModel {
    var min = 1
    var max = 100
    var secret = Random.nextInt(100) + 1

    fun isGuessTooHigh(guess: Int): Boolean = guess > secret

    fun isGuessTooLow(guess: Int): Boolean = guess < secret

    fun resetGame() {
        min = 1
        max = 100
        secret = Random.nextInt(100) + 1
    }
}
