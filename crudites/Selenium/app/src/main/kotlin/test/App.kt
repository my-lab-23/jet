package test

import org.openqa.selenium.Alert
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

class MyTest {

    private val driver: WebDriver by lazy {

        System.setProperty("webdriver.chrome.driver", "/home/ema/chromedriver_linux64/chromedriver")
        val options = ChromeOptions()
        options.addArguments("--headless")
        ChromeDriver(options)
    }

    private fun alert() {

        val alert: Alert = driver.switchTo().alert()
        alert.sendKeys(System.getenv("CRUDITES_API_KEY"))
        alert.accept()
    }

    fun test(id: Int, data: String = "", button: Char): String? {

        try {
            driver.get("https://crudites.netlify.app/")

            alert()

            val idInput = driver.findElement(By.id("id"))
            val dataInput = driver.findElement(By.id("data"))
            val createButton = driver.findElement(By.id("$button"))

            idInput.sendKeys("$id")
            dataInput.sendKeys(data)
            createButton.click()
            Thread.sleep(2000)

            val resultArea = driver.findElement(By.id("resultText"))

            return resultArea.text

        } finally { driver.quit() }
    }
}
