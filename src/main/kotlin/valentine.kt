import kotlinx.browser.document
import kotlinx.html.TagConsumer
import kotlinx.html.dom.append
import kotlinx.html.js.table
import kotlinx.html.js.tbody
import kotlinx.html.js.td
import kotlinx.html.js.tr
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.Node
import kotlin.math.ceil
import kotlin.math.sqrt

const val plainText = "Lorem ipsum etcetera"
const val valentineTitle = "Krásného Valentýna"

fun Node.card(msg: String) {
    val text = msg.replace(Regex("[\\W]"), "")
    val rows = ceil(sqrt(text.length.toDouble())).toInt()
    val cols = ceil(text.length.toDouble() / rows).toInt()
    console.log("rows = $rows, cols = $cols")

    append {
        renderTable(rows, cols, text)
    }
}

private fun TagConsumer<HTMLElement>.renderTable(
    rows: Int,
    cols: Int,
    content: String
) {
    var index: Int
    var value:Char
    table(classes = "table table-bordered") {
        tbody {
            for (row in 0 until rows) {
                tr {
                    for (col in 0 until cols) {
                        td {
                            index = row * cols + col
                            value = if (index < content.length) content[index] else ' '
                            text(value.toString())
                        }
                    }
                }
            }
        }
    }
}

fun Node.card(inputKeySelector: String, msg: String) {
    val text = msg.replace(" ", "")
    val rows = ceil(sqrt(text.length.toDouble())).toInt()
    val cols = ceil(text.length.toDouble() / rows).toInt()
    console.log("rows = $rows, cols = $cols")
    val content = text.toCharArray()
    console.log("find $inputKeySelector")
    val key = document.querySelector(inputKeySelector)?.unsafeCast<HTMLInputElement>()?.value ?: ""
    console.log("input value: $key")
    var index: Int

    append {
        table(classes = "table table-borderless") {
            tbody {
                for (row in 0 until rows) {
                    tr {
                        for (col in 0 until cols) {
                            td {
                                index = row * cols + col
                                val value = if (index < content.size && key.isNotBlank())
                                    content[index].code(key[index % key.length])
                                else ' '
                                text(value.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun Char.code(s: Char): Char {
    return ((orderValue() + s.orderValue()) % 26 + ('A'.toInt())).toChar()
}

private fun Char.orderValue() = toUpperCase().toInt() - 'A'.toInt()
