package com.example.novena2

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.novena2.ui.theme.Novena2Theme
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.novena2", appContext.packageName)
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    fun lauchGUI() {

        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch {

            clientWebSocket.connect()
            Synchro.update()

            greens = Aux.convertToFalseArrayIfAllTrue(greens)

            composeTestRule.setContent {
                Novena2Theme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Grid()
                    }
                }
            }
        }
    }

    private val customDay = 15

    @Test
    fun customDay() {

        mockkObject(MyTime)
        every { MyTime.getCurrentDay() } returns customDay

        lauchGUI()
        while(true) { /* - */ }
    }
}
