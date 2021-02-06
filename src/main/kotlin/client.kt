import kotlinx.html.div
import kotlinx.html.dom.append
import org.w3c.dom.Node
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.InputType
import kotlinx.html.input
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement

fun main() {
    window.onload = {
        valentineTitle.also { document.title = HEART + it + HEART }
        document.getElementById("root")?.let {
            it.card(plainText)
            it.append {
                input(InputType.text, name = "key", classes = "form-control text-align") {
                    onChangeFunction = { event ->
                        console.log("${this.name} was changed to")
                        val target = event.target.unsafeCast<HTMLInputElement>()
                        console.log(target.value)
                        document.getElementById("table")?.card("#root input[name=${this.name}]", plainText)
                    }
                }
            }
        }

    }
}

fun Node.sayHello() {
    append {
        div {
            +"Hello from JS"
        }
    }
}

const val HEART = "\u2764"
