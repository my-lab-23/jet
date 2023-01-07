package com.example.trivia.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trivia.loadCorrect
import com.example.trivia.loadIncorrect
import com.example.trivia.loadQuestion

@Composable
fun MyApp(myViewModel: MyViewModel = viewModel()) {

    val myUiState by myViewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .verticalScroll(rememberScrollState())
    ) {

        val question = loadQuestion(myUiState.counter)
        val correct = loadCorrect(myUiState.counter)
        val incorrect = loadIncorrect(myUiState.counter)
        val allAnswers = mutableListOf<String>()

        //

        allAnswers.add(correct)
        for(i in incorrect) allAnswers.add(i)

        if(myUiState.correctNumber + myUiState.incorrectNumber < 9) {
            allAnswers.shuffle()
        }

        //

        Text(question, fontSize = 25.sp)

        for(i in allAnswers) AnswerButton(myUiState, myViewModel, i, correct)

        Text("Correct: ${myUiState.correctNumber}", fontSize = 25.sp)
        Text("Incorrect: ${myUiState.incorrectNumber}", fontSize = 25.sp)

        //

        val totalAnswers = myUiState.correctNumber + myUiState.incorrectNumber

        if(totalAnswers in 1..9) Solution(myUiState.counter-1)
        if(totalAnswers == 10) Solution(9)
    }
}

@Composable
fun AnswerButton(myUiState: MyUiState, myViewModel: MyViewModel, i: String, correct: String) {

    Button(onClick = {

        if(myUiState.correctNumber + myUiState.incorrectNumber < 10) {
            if (i == correct) {
                myViewModel.increaseCorrectNumber()
            } else {
                myViewModel.increaseIncorrectNumber()
            }
        }

        if(myUiState.counter < 9) { myViewModel.increaseCounter() }

    }) {
        Text(i, fontSize = 25.sp)
    }
}

@Composable
fun Solution(i: Int) {

    val pQuestion = loadQuestion(i)
    val pCorrect = loadCorrect(i)
    val text = "$pQuestion $pCorrect"
    Text(text, fontSize = 25.sp)
}
