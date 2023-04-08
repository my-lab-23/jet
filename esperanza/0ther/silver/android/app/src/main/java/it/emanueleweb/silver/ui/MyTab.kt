package it.emanueleweb.silver.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Composable
fun SwitchableTabs() {

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabData = listOf("Silver", "ADA")

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = Color.Gray,
            contentColor = Color.White
        ) {
            tabData.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }
        when (selectedTabIndex) {
            0 -> Tab1Content()
            1 -> Tab2Content()
        }
    }
}

@Composable
fun Tab1Content() {
    MySilverCard()
}

@Composable
fun Tab2Content() {
    MyADACard()
}
