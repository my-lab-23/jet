package it.emanueleweb.solaria

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.espresso.Espresso
import it.emanueleweb.solaria.ui.MyAbout
import it.emanueleweb.solaria.ui.MyInfo
import it.emanueleweb.solaria.ui.MyEntity
import it.emanueleweb.solaria.ui.theme.SolariaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test

class MyNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myNavigationTest() {

        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch {

            MyIO.NetworkIO.loadData()

            composeTestRule.setContent {

                SolariaTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {

                        SetBarColor()

                        val navController = rememberNavController()

                        NavHost(
                            navController = navController,
                            startDestination = "myEntity/{switch}"
                        ) {
                            composable("myEntity/{switch}") {
                                val switch = it.arguments?.getString("switch") ?: "luoghi"
                                MyEntity(navController, switch)
                            }
                            composable("myInfo/{id}") {
                                val id = it.arguments?.getString("id")
                                if (id != null) { MyInfo(id) }
                            }
                            composable("myAbout") { MyAbout() }
                        }
                    }
                }
            }
        }

        myClick(composeTestRule, "l0")
        myClick(composeTestRule, "l1")
        myScrollClick(composeTestRule, "l2")
        myClick(composeTestRule, "Attrattive", false)
        myClick(composeTestRule, "a0")
        myClick(composeTestRule, "a1")
        myScrollClick(composeTestRule, "a2")
        myClick(composeTestRule, "Eventi", false)
        myClick(composeTestRule, "e0")
        myClick(composeTestRule, "e1")
        myScrollClick(composeTestRule, "e2")
    }
}

const val s = 0

fun myScrollClick(
    composeTestRule: ComposeContentTestRule,
    contentDescription: String) {
    mySleep(composeTestRule, s)
    composeTestRule.onNodeWithContentDescription(contentDescription)
        .performScrollTo()
        .performClick()
    mySleep(composeTestRule, s)
    Espresso.pressBack()
    mySleep(composeTestRule, s)
}

fun myClick(composeTestRule: ComposeContentTestRule,
            contentDescription: String,
            back: Boolean = true
    ) {
    mySleep(composeTestRule, s)
    composeTestRule.onNodeWithContentDescription(contentDescription)
        .performClick()
    if(back) {
        mySleep(composeTestRule, s+1)
        Espresso.pressBack()
    }
    mySleep(composeTestRule, s)
}

fun mySleep(composeTestRule: ComposeContentTestRule, seconds: Int) {

    try {
        composeTestRule.waitUntil((seconds*1000).toLong()) { false }
    } catch (e: ComposeTimeoutException) {
        // do nothing
    }
}
