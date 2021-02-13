import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.dom.prepend
import kotlinx.html.js.button
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.Node
import org.w3c.xhr.XMLHttpRequest

fun main() {

    val httpReq = XMLHttpRequest()
    val contentClass = "encrypted"
    val contentSelector = ".$contentClass"

    httpReq.onreadystatechange = {
        if (httpReq.readyState == 4.toShort() && httpReq.status == 200.toShort()) {

            document.getElementById("root")?.let { root ->

                root.prepend {

                    div("input-group") {
                        div("input-group-prepend") {
                            div("input-group-text") {
                                i("fas fa-key")
                            }
                        }
                        input(InputType.text, name = "key", classes = "form-control text-center") {
                            onChangeFunction = { event ->
                                console.log("${this.name} was changed to")
                                val target = event.target.unsafeCast<HTMLInputElement>()
                                console.log(target.value)
                                document.getElementById("table")?.let { table ->
                                    table.innerHTML = ""
                                    val msg = document.querySelector("$contentSelector .table")?.textContent ?: ""
                                    table.card(msg, "#root input[name=${this.name}]")
                                    table.formatButton()
                                }
                            }
                        }
                    }

                    div(contentClass).card(httpReq.responseText)
                }
            }
            js("""$('[data-toggle="popover"]').popover()""")
        }
    }
    httpReq.open("GET", "present_simplified.txt", true)
    httpReq.send()

    window.onload = {
        valentineTitle.also { document.title = HEART + it + HEART }

    }
}

private fun Node.formatButton() {
    append {
        button(classes = "btn btn-danger btn-lg") {
            title = "Nápověda 1"
            attributes["data-toggle"] = "popover"
            attributes["data-content"] = "obsah nápovědy"
        }.textContent = "Prettify"
    }
}

private fun toParagraphs(content: String) =
    "<p>${content.replace(Regex("[\r\n]"), "</p><p>")}</p>"


const val HEART = "\u2764"
