package com.example.trivia.ui

import com.example.trivia.MySetting

data class MyUiState(

    var difficultyLevel: String = MySetting.difficultyLevel,
    var category: String = "Geography",

    //

    val counter: Int = 0,
    val correctNumber: Int = 0,
    val incorrectNumber: Int = 0
)
