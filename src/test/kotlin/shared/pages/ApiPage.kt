package shared.pages

// TODO make method enum

open class ApiPage(private val section: String, private val method: String, private val operation: String){
    val topElement = "div#operations-$section-$operation.opblock-${method.toLowerCase()}"
    val header = "$topElement div.opblock-summary"

    val isOpen = "$topElement.is-open"
    val tryItOutButton = "button.btn.try-out__btn"
    val description = ".opblock-description"
}