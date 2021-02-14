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

const val valentineTitle = "Krásného Valentýna"
const val baseOrder = 'A'.toInt()

fun Node.card(msg: String, inputKeySelector: String = "") {
//    val text = msg.replace(Regex("[\\W]"), "")
    val text = msg
    val rows = ceil(sqrt(text.length.toDouble())).toInt()
    val cols = ceil(text.length.toDouble() / rows).toInt()
    console.log("rows = $rows, cols = $cols")
    console.log("find $inputKeySelector")
    val key = if (inputKeySelector.isNotBlank()) document.querySelector(inputKeySelector)
        ?.unsafeCast<HTMLInputElement>()?.value ?: ""
    else ""
    console.log("input value: $key")

    append {
        renderTable(rows, cols, text, key)
    }
}

private fun TagConsumer<HTMLElement>.renderTable(
    rows: Int,
    cols: Int,
    content: String,
    key: String = ""
) {
    var index: Int
    var value: Char

    table(classes = "table table-bordered") {
        tbody {
            for (row in 0 until rows) {
                tr {
                    for (col in 0 until cols) {
                        td {
                            index = row * cols + col
                            value = if (index < content.length) {
                                if (key.isNotBlank()) content[index].decode(key[index % key.length])
                                else content[index]
                            } else ' '
                            text(value.toString())
                        }
                    }
                }
            }
        }
    }
}

private fun Char.decode(s: Char): Char =
    if (!isWhitespace()) ((orderValue() - s.orderValue() + 26) % 26 + (baseOrder)).toChar() else this

private fun Char.code(s: Char): Char =
    if (!isWhitespace()) ((orderValue() + s.orderValue()) % 26 + (baseOrder)).toChar() else this

private fun Char.orderValue() = toUpperCase().toInt() - baseOrder
