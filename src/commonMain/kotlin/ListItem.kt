@kotlinx.serialization.Serializable
data class ListItem(val desc: String, val priority: Int) {
    val id: Int = desc.hashCode()

    companion object {
        const val path = "/shoppingList"
    }
}

val shoppingList = mutableListOf(
    ListItem("Cucumbers 🥒", 1),
    ListItem("Tomatoes 🍅", 2),
    ListItem("Orange Juice 🍊", 3)
)
