import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.browser.document
import kotlinx.browser.window
import react.create
import react.dom.client.createRoot

// only needed until https://youtrack.jetbrains.com/issue/KTOR-453 is resolved
val endpoint = window.location.origin

val jsonClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun getShoppingList(): List<ListItem> {
    return jsonClient.get(endpoint + ListItem.path).body()
}

suspend fun addShoppingListItem(item: ListItem) {
    jsonClient.post(endpoint + ListItem.path) {
        contentType(ContentType.Application.Json)
        setBody(item)
    }
}

suspend fun deleteShoppingListItem(item: ListItem) {
    jsonClient.delete(endpoint + ListItem.path + "/${item.id}")
}

fun main() {
    val container = document.getElementById("root") ?: error("Couldn't find container!")
    createRoot(container).render(App.create())
}
