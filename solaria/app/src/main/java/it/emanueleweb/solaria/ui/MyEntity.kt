package it.emanueleweb.solaria.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import it.emanueleweb.solaria.MyData
import it.emanueleweb.solaria.MyIO
import it.emanueleweb.solaria.ui.theme.SolariaTheme

@Composable
fun MyEntity(navController: NavHostController, switch: String) {

    val search = rememberSaveable { mutableStateOf(0) }
    val favourite = rememberSaveable { mutableStateOf(0) }

    val configuration = LocalConfiguration.current

    val fixedRowHeight = 100.dp
    val screenHeight = configuration.screenHeightDp.dp
    val rows = screenHeight / fixedRowHeight
    val ratio = rows - 1

    SolariaTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(ratio)
                ) {

                    Column(
                        Modifier.verticalScroll(rememberScrollState())
                    ) {
                        MySearch(search, favourite, navController)

                        //

                        val context = LocalContext.current

                        if (!MyIO.NetworkIO.isNetworkAvailable(context)) { MyNoConnection(navController) }

                        //

                        else if (favourite.value>0 && search.value>0) {

                            MyFavourite(navController, MyData.luoghiSearch)
                            MyFavourite(navController, MyData.attrattiveSearch)
                            MyFavourite(navController, MyData.eventiSearch)
                        }

                        else if (favourite.value>0 && search.value==0) {

                            MyFavourite(navController, MyData.luoghi)
                            MyFavourite(navController, MyData.attrattive)
                            MyFavourite(navController, MyData.eventi)
                        }

                        //

                        else if (search.value>0) { Search(switch, navController) }
                        else if (search.value==0) { NotSearch(switch, navController) }

                        //
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color(0xFFABCDEF))
                ) {

                    MyButton("Luoghi", navController, search)
                    Spacer(modifier = Modifier.width(32.dp))
                    MyButton("Attrattive", navController, search)
                    Spacer(modifier = Modifier.width(32.dp))
                    MyButton("Eventi", navController, search)
                }
            }
        }
    }
}

@Composable
fun NotSearch(switch: String, navController: NavHostController) {

    when (switch) {
        "eventi" -> {

            MyData.eventi.forEach {
                MyCard(
                    it.id,
                    it.name,
                    it.description,
                    navController
                )
            }
        }
        "attrattive" -> {

            MyData.attrattive.forEach {
                MyCard(
                    it.id,
                    it.name,
                    it.description,
                    navController
                )
            }
        }
        else -> {

            MyData.luoghi.forEach {
                MyCard(
                    it.id,
                    it.name,
                    it.description,
                    navController
                )
            }
        }
    }
}

@Composable
fun Search(switch: String, navController: NavHostController) {

    when (switch) {
        "eventi" -> {

            MyData.eventiSearch.forEach {
                MyCard(
                    it.id,
                    it.name,
                    it.description,
                    navController
                )
            }
        }
        "attrattive" -> {

            MyData.attrattiveSearch.forEach {
                MyCard(
                    it.id,
                    it.name,
                    it.description,
                    navController
                )
            }
        }
        else -> {

            MyData.luoghiSearch.forEach {
                MyCard(
                    it.id,
                    it.name,
                    it.description,
                    navController
                )
            }
        }
    }
}

@Composable
fun MyFavourite(
    navController: NavHostController,
    entityLocal: List<MyData.Entity>
) {
    entityLocal.forEach {

        if (it.favourite) {
            MyCard(
                it.id,
                it.name,
                it.description,
                navController
            )
        }
    }
}
