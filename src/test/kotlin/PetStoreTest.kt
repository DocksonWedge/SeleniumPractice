import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import shared.actions.ApiSelection.closeApiPage
import shared.actions.ApiSelection.openApiPage
import shared.pages.FindPetsByStatus


class PetStoreTest {

    val baseUrl = "https://petstore.swagger.io/"
    private lateinit var driver: WebDriver

    @BeforeEach
    fun setupTest() {
        WebDriverManager.chromedriver().setup() //TODO why doesn't this work in BeforeAll?
        driver = ChromeDriver()
        driver.get(baseUrl)
    }

    @AfterEach
    fun teardown() {
            driver.quit()
    }

    @TestFactory
    fun `Check that api pages open`() = listOf(
            FindPetsByStatus()
                to "For valid response try integer IDs with value >= 1 and <= 10"
    ).map { (api, description) ->
        DynamicTest.dynamicTest(
                "When I open an api page then I get the right description and close it."
        ) {
            driver.openApiPage(api)

            driver.closeApiPage(api)
            println("done")
        }
    }
}

