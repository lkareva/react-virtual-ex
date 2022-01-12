import kotlinx.css.*
import kotlinx.css.properties.translateY
import kotlinx.html.id
import react.*
import react.dom.attrs
import react.virtual.VirtualOptions
import react.virtual.useVirtual
import styled.css
import styled.styledDiv

external interface RowVirtualizerDynamicProps : Props {
    var rows: Array<Int>
}
fun VirtualOptions(): VirtualOptions<Any> = js("{}")

val RowVirtualizerDynamic = fc("RowVirtualizerDynamic") { props: RowVirtualizerDynamicProps ->
    val parenttRef = useRef<Any>()
    val rowVirtualizer = useVirtual(
        options = VirtualOptions().apply {
            size = props.rows.size
            parentRef = parenttRef
        }
    )
    styledDiv {
        ref = parenttRef
        attrs {
            id = "List"
        }
        css {
            height = 200.px
            width = 400.px
            overflow = Overflow.auto
        }
        styledDiv {
            css {
                height = rowVirtualizer.totalSize.px
                width = 100.pct
                position = Position.relative
            }
            rowVirtualizer.virtualItems.map { virtualRow ->
                styledDiv {
                    attrs.id = if (virtualRow.index % 2 == 0) "ListItemOdd" else "ListItemEven"
                    css {
                        position = Position.absolute
                        top = 0.px
                        left = 0.px
                        width = 100.pct
                        height = virtualRow.size.px
                        transform.translateY(virtualRow.start.px)
                    }
                    key = "${virtualRow.index}"
                    ref = virtualRow.measureRef
                    +"Row ${virtualRow.index}"
                }
            }
        }
    }
}
fun RBuilder.rowVirtualizerDynamic(
    rows: Array<Int>
)= child(RowVirtualizerDynamic) {
    attrs.rows = rows
}
