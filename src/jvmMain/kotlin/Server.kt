import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 9090
    embeddedServer(Netty, port) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Post)
            allowMethod(HttpMethod.Delete)
            anyHost()
        }
        install(Compression) {
            gzip()
        }
        routing {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            static("/") {
                resources("")
            }
            route(ListItem.path) {
                get {
                    call.respond(shoppingList)
                }
                get("/{id}") {
                    val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request.")
                    val item = shoppingList.firstOrNull { it.id == id }
                    if (item != null) {
                        call.respond(item)
                    } else {
                        call.respondText("404 Item not found!")
                    }
                }
                post {
                    shoppingList += call.receive<ListItem>()
                    call.respond(HttpStatusCode.OK)
                }
                delete("/{id}") {
                    val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request.")
                    val result = shoppingList.removeAll { it.id == id }
                    if (result) {
                        call.respondText("Item deleted successfully!")
                    } else {
                        call.respondText("No item found with id = $id")
                    }
                }
            }
        }
    }.start(wait = true)
}
