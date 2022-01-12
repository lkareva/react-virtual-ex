import kotlinx.browser.document
import react.dom.h3
import react.dom.render
var i = 0
val rows = Array(10000){i++}
var j = 0
val columns = Array(10000){j++}
fun main() {
    document.getElementById("root")?.let {
        render(it) {
            h3 {
                +"Rows"
            }
            rowVirtualizerDynamic(rows)
            h3 {
                +"Columns"
            }
            columnVirtualizerDynamic(columns)
            h3 {
                +"Grid"
            }
            gridVirtualizerDynamic(rows, columns)
        }
    }
}