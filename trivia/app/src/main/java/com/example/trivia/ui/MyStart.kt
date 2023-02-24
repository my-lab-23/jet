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
import androidx.navigation.NavHostController
import com.example.trivia.MySetting
import com.example.trivia.loadJsonFile
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun MyStart(
    navController: NavHostController,
    myViewModel: MyViewModel = viewModel()
) {

    val myUiState by myViewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .verticalScroll(rememberScrollState())
    ) {

        StartButton(navController)
        DifficultyButton(myViewModel, myUiState)
        CategoryButton(myViewModel, myUiState)
    }
}

@Composable
fun StartButton(navController: NavHostController) {

    val coroutineScope = rememberCoroutineScope()

    Button(onClick = {

        val c = coroutineScope.async { loadJsonFile() }
        coroutineScope.launch {
            c.await()
            navController.navigate("MyApp")
        }
    }) {
        Text("Start", fontSize = 25.sp)
    }
}

@Composable
fun DifficultyButton(myViewModel: MyViewModel, myUiState: MyUiState) {

    Button(onClick = {

        when (MySetting.difficultyLevel) {
            "Easy" -> {
                MySetting.difficultyLevel = "Medium"
                myViewModel.changeDifficultyLevel("Medium")
            }
            "Medium" -> {
                MySetting.difficultyLevel = "Hard"
                myViewModel.changeDifficultyLevel("Hard")
            }
            "Hard" -> {
                MySetting.difficultyLevel = "Easy"
                myViewModel.changeDifficultyLevel("Easy")
            }
        }

    }) {
        Text(myUiState.difficultyLevel, fontSize = 25.sp)
    }
}

@Composable
fun CategoryButton(myViewModel: MyViewModel, myUiState: MyUiState) {

    Button(onClick = {

        when (MySetting.category) {
            "22" -> {
                MySetting.category = "23"
                myViewModel.changeCategory("History")
            }
            "23" -> {
                MySetting.category = "22"
                myViewModel.changeCategory("Geography")
            }
        }

    }) {
        Text(myUiState.category, fontSize = 25.sp)
    }
}
