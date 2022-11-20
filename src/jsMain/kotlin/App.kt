import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul

private val scope = MainScope()

val App = FC<Props> {
    var shoppingList by useState(emptyList<ListItem>())

    useEffectOnce {
        scope.launch {
            shoppingList = getShoppingList()
        }
    }

    h1 { +"Shopping List" }

    ul {
        inputField {
            onSubmit = { input ->
                val cartItem = ListItem(
                    input.replace("!", ""),
                    input.count { it == '!' }
                )
                scope.launch {
                    addShoppingListItem(cartItem)
                    shoppingList = getShoppingList()
                }
            }
        }

        shoppingList.sortedByDescending(ListItem::priority).forEach { item ->
            li {
                key = item.toString()
                onClick = {
                    scope.launch {
                        deleteShoppingListItem(item)
                        shoppingList = getShoppingList()
                    }
                }
                +"[${item.priority}] ${item.desc}"
            }
        }
    }
}
