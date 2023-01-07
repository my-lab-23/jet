package com.example.jalaspot.ui

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import com.example.jalaspot.R
import com.example.jalaspot.ui.theme.Purple200

@Composable
fun MyApp(navController: NavHostController) {

    val stringList1 = listOf(
        "preferiti", "saved", "dropbox",
        "load", "save", "remove"
    )

    val intList1 = listOf(
        R.drawable.preferiti, R.drawable.saved, R.drawable.dropbox,
        R.drawable.load, R.drawable.save, R.drawable.remove
    )

    val stringList2 = listOf(
        "preferiti", "load",
        "saved", "save",
        "dropbox", "remove"
    )

    val intList2 = listOf(
        R.drawable.preferiti, R.drawable.load,
        R.drawable.saved, R.drawable.save,
        R.drawable.dropbox, R.drawable.remove
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
                F(listOf(2, 3), stringList1, intList1, navController)
            }
            else -> {
                F(listOf(3, 2), stringList2, intList2, navController)
            }
        }
    }
}
