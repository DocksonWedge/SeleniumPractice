package shared.actions

import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions.not
import org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated
import shared.pages.ApiPage
import java.time.Duration

object ApiSelection {
    private val defaultWait = Duration.ofSeconds(1)
    private val defaultInterval = Duration.ofMillis(100)

    fun WebDriver.openApiPage(api: ApiPage) {
        val openElements = this.findElements(By.cssSelector(api.isOpen))
        // if it's already open, just leave it. We assume calling openApiPage means you want it open
        if (openElements.isEmpty()) {
            this.waitUntilClicked(api)
            this.waitUntilOpen(api)
        }
    }

    fun WebDriver.closeApiPage(api: ApiPage) {
        val openElements = this.findElements(By.cssSelector(api.isOpen))
        // if it's already open, just leave it. We assume calling openApiPage means you want it open
        if (openElements.isNotEmpty()) {
            this.waitUntilClicked(api)
            this.waitUntilClosed(api)
        }
    }

    private fun WebDriver.waitUntilClicked(api: ApiPage) {
        WebDriverWait(this, defaultWait)
                .pollingEvery(defaultInterval)
                .ignoring(StaleElementReferenceException::class.java)
                .ignoring(NoSuchElementException::class.java)
                .until { this.findElement(By.cssSelector(api.header)).click() }
    }

    private fun WebDriver.waitUntilOpen(api: ApiPage) {
        WebDriverWait(this, defaultWait)
                .pollingEvery(defaultInterval)
                .ignoring(StaleElementReferenceException::class.java)
                .until { presenceOfElementLocated(By.cssSelector(api.isOpen)) }
    }

    private fun WebDriver.waitUntilClosed(api: ApiPage) {
        WebDriverWait(this, defaultWait)
                .pollingEvery(defaultInterval)
                .ignoring(StaleElementReferenceException::class.java)
                .until { //todo not working :/
                    not { presenceOfElementLocated(By.cssSelector(api.isOpen)) }
                }
    }
}