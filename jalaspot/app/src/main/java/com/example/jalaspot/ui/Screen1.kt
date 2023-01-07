package com.example.jalaspot.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.jalaspot.R
import com.example.jalaspot.ui.theme.Purple200

@Composable
fun Screen1() {

    val stringList1 = listOf(
        "haydn", "mozart", "invernizzi",
        "faust", "haydn2032", "?"
    )

    val intList1 = listOf(
        R.drawable.image, R.drawable.image, R.drawable.image,
        R.drawable.image, R.drawable.image, R.drawable.image
    )

    val configuration = LocalConfiguration.current

    Column(
        modifier = Modifier
            .background(Purple200)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Row { TopBar() }

        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                F(listOf(2, 3), stringList1, intList1)
            }
            else -> {
                F(listOf(3, 2), stringList1, intList1)
            }
        }
    }
}
