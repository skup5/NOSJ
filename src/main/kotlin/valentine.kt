import kotlinx.html.dom.append
import kotlinx.html.js.table
import kotlinx.html.js.td
import kotlinx.html.js.tr
import org.w3c.dom.Node
import kotlin.math.ceil
import kotlin.math.sqrt

const val plainText = "Lorem ipsum etcetera"
const val valentineTitle = "Krásného Valentýna"

fun Node.card(key: String) {
    val text = plainText.replace(" ", "")
    val rows = ceil(sqrt(text.length.toDouble())).toInt()
    val cols = ceil(text.length.toDouble() / rows).toInt()
    val content = text.toCharArray()
    console.log("rows = $rows, cols = $cols")

    append {
        table {
            for (row in 0 until rows) {
                tr {
                    for (col in 0 until cols) {
                        td {
                            text(row * cols + col)
//                            text(content[row * cols + col].toString())
                        }
                    }
                }
            }
        }
    }
}