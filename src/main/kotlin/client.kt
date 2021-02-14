import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.dom.removeClass
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.dom.prepend
import kotlinx.html.js.button
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.*
import org.w3c.xhr.XMLHttpRequest

fun main() {

    val httpReq = XMLHttpRequest()
    val contentClass = "encrypted"
    val contentSelector = ".$contentClass"

    httpReq.onreadystatechange = {
        if (httpReq.readyState == 4.toShort() && httpReq.status == 200.toShort()) {

            document.getElementById("content")?.let { root ->

                root.prepend {

                    div("input-group row justify-content-center d-none") {
                        div("input-group-prepend") {
                            div("input-group-text") {
                                i("fas fa-key")
                            }
                        }
                        input(InputType.text, name = "key", classes = "form-control text-center") {
                            size = "10"
                            onChangeFunction = { event ->
                                console.log("${this.name} was changed to")
                                val target = event.target.unsafeCast<HTMLInputElement>()
                                console.log(target.value)
                                document.getElementById("table")?.let { table ->
                                    table.innerHTML = ""
                                    val msg = document.querySelector("$contentSelector .table")?.textContent ?: ""
                                    table.card(msg, "#root input[name=${this.name}]")
                                    document.querySelector("#table table")?.scrollIntoView()
                                    table.formatButton("#table table")
                                }
                            }
                        }
                    }

                    div(contentClass).card(httpReq.responseText)

                }
            }

            document.getElementById("root")?.helpButtons()

        }

        document.querySelector("h1")?.scrollIntoView()
    }
    httpReq.open("GET", "present_simplified.txt", true)
    httpReq.send()

    window.onload = {
        valentineTitle.also { document.title = HEART + it + HEART }

    }
}

private fun Element.formatButton(contentSelector: String) {
    append {
        modalWindowButton("Zformátovat", contentSelector)
    }
}

private fun Node.helpButtons() {
    append {
        div(classes = "d-flex justify-content-between mt-5") {
            helpButton("Nápověda", "Vigenèr")
            helpButton("Nápověda", """<i class="fas fa-key"/> 6""")
            button(classes = "btn btn-danger btn-lg btn-help rounded-circle") {
                onClickFunction = {
                    document.querySelector(".input-group")?.removeClass("d-none")
                }
                i("far fa-question-circle")
            }
        }
    }
}

fun TagConsumer<HTMLElement>.helpButton(helpTitle: String, helpContent: String = "") {
    button(classes = "btn btn-danger btn-lg btn-help rounded-circle") {
        title = helpTitle
        attributes["data-toggle"] = "popover"
        attributes["data-trigger"] = "focus"
        attributes["data-html"] = "true"
        attributes["data-content"] = helpContent

        i("far fa-question-circle")
    }
}

/**
 * Button trigger modal
 */
fun TagConsumer<HTMLElement>.modalWindowButton(btnLabel: String, contentSelector: String) {
    button(classes = "btn bg-pink text-light btn-modal") {
        type = ButtonType.button
        attributes["data-toggle"] = "modal"
        attributes["data-target"] = "#modalWindow"
        +btnLabel
    }.addEventListener("click", { event ->
        document.getElementById("modalWindowLabel")?.textContent =
            document.getElementsByTagName("h1")[0]?.textContent ?: ""
        val content = document.querySelector(contentSelector)?.textContent ?: ""
        document.getElementById("modalWindowContent")?.innerHTML = toParagraphs(content)
    })
}

private fun toParagraphs(content: String) =
    "<p>${content.replace(Regex("[\r\n]"), "<br/>")}</p>"


const val HEART = "\u2764"
