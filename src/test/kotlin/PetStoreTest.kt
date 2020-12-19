import io.github.bonigarcia.wdm.WebDriverManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import shared.actions.ApiRead.readDescription
import shared.actions.ApiSelection.closeApiPage
import shared.actions.ApiSelection.openApiPage
import shared.pages.DeleteOrder
import shared.pages.FindPetsByStatus
import shared.pages.UpdateUser


class PetStoreTest {

    val baseUrl = "https://petstore.swagger.io/"
    private lateinit var driver: WebDriver

    @BeforeEach
    fun setupTest() {
        WebDriverManager.chromedriver().setup() //TODO why doesn't this work in BeforeAll?
        val options = ChromeOptions()
        options.setHeadless(true)
        driver = ChromeDriver(options)
        driver.get(baseUrl)
    }

    @AfterEach
    fun teardown() {
            driver.quit()
    }

    @TestFactory
    fun `Check that api pages open`() = listOf(
            FindPetsByStatus()
                to "Multiple status values can be provided with comma separated strings",
            DeleteOrder()
                to "For valid response try integer IDs with positive integer value.",
            UpdateUser()
                to "This can only be done by the logged in user."
    ).map { (api, description) ->
        DynamicTest.dynamicTest(
                "When I open an page ${api.topElement} then I get the right description $description."
        ) {
            driver.openApiPage(api)
            assertThat(driver.readDescription(api)).contains(description)
            driver.closeApiPage(api)
        }
    }
}

