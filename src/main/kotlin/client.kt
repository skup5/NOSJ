import kotlinx.html.div
import kotlinx.html.dom.append
import org.w3c.dom.Node
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.InputType
import kotlinx.html.input
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.p
import org.w3c.dom.HTMLInputElement
import org.w3c.xhr.XMLHttpRequest

fun main() {

    val httpReq = XMLHttpRequest()
    httpReq.onreadystatechange = {
        if (httpReq.readyState == 4.toShort() && httpReq.status == 200.toShort()) {
//            window.onload = {
            document.getElementById("table")?.card(httpReq.responseText)
            document.getElementById("demo")?.innerHTML = toParagraphs(httpReq.responseText)

//            }
        }
    }
    httpReq.open("GET", "doc.txt", true)
    httpReq.send()

    window.onload = {
        valentineTitle.also { document.title = HEART + it + HEART }
        /* document.getElementById("root")?.let {
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
         }*/

    }
}

private fun toParagraphs(content: String) =
    "<p>${content.replace(Regex("[\r\n]"), "</p><p>")}</p>"


const val HEART = "\u2764"
