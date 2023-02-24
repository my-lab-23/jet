package com.example.trivia.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyViewModel : ViewModel() {

    // Scrittura
    private val _uiState = MutableStateFlow(MyUiState())
    // Lettura
    val uiState: StateFlow<MyUiState> = _uiState.asStateFlow()

    fun changeDifficultyLevel(level: String) {
        _uiState.update { currentState -> currentState.copy(difficultyLevel = level) }
    }

    fun changeCategory(category: String) {
        _uiState.update { currentState -> currentState.copy(category = category) }
    }

    //

    fun increaseCounter() {
        val counter = _uiState.value.counter + 1
        _uiState.update { currentState -> currentState.copy(counter = counter) }
    }

    fun increaseCorrectNumber() {
        val correctNumber = _uiState.value.correctNumber + 1
        _uiState.update { currentState -> currentState.copy(correctNumber = correctNumber) }
    }

    fun increaseIncorrectNumber() {
        val incorrectNumber = _uiState.value.incorrectNumber + 1
        _uiState.update { currentState -> currentState.copy(incorrectNumber = incorrectNumber) }
    }
}
