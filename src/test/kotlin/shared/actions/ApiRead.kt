package shared.actions

import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import shared.pages.ApiPage
import java.time.Duration

object ApiRead {
    private val defaultWait = Duration.ofSeconds(1)
    private val defaultInterval = Duration.ofMillis(100)

    fun WebDriver.readDescription(api: ApiPage): String {
        val descriptionSelector = By.cssSelector("${api.topElement} ${api.description}")
        // TODO pull this wait to shared function
        return WebDriverWait(this, defaultWait)
            .pollingEvery(defaultInterval)
            .ignoring(StaleElementReferenceException::class.java)
            .ignoring(NoSuchElementException::class.java)
            .until {
                findElement(descriptionSelector).text
            } ?: ""
    }
}