import kotlinx.css.*
import kotlinx.css.properties.translateX
import kotlinx.html.id
import react.Props
import react.RBuilder
import react.dom.attrs
import react.fc
import react.useRef
import react.virtual.useVirtual
import styled.css
import styled.styledDiv

external interface ColumnVirtualizerDynamicProps : Props {
    var columns: Array<Int>
}
val ColumnVirtualizerDynamic = fc("ColumnVirtualizerDynamic") { props: ColumnVirtualizerDynamicProps ->
    val parenttRef = useRef<Any>()
    val columnVirtualizer = useVirtual(
        options = VirtualOptions().apply {
            horizontal = true
            size = props.columns.size
            parentRef = parenttRef
        }
    )
    styledDiv {
        ref = parenttRef
        attrs {
            id = "List"
        }
        css {
            width = 400.px
            height = 100.px
            overflow = Overflow.auto
        }
        styledDiv {
            css {
                width = columnVirtualizer.totalSize.px
                height = 100.pct
                position = Position.relative
            }
            columnVirtualizer.virtualItems.map { virtualColumn ->
                styledDiv {
                    attrs.id = if (virtualColumn.index % 2 == 0) "ListItemOdd" else "ListItemEven"
                    css {
                        position = Position.absolute
                        top = 0.px
                        left = 0.px
                        height = 100.pct
                        width = virtualColumn.size.px
                        transform.translateX(virtualColumn.start.px)
                    }
                    key = "${virtualColumn.index}"
                    ref = virtualColumn.measureRef
                    +"Column ${virtualColumn.index}"
                }
            }
        }
    }
}
fun RBuilder.columnVirtualizerDynamic(
    columns: Array<Int>
)= child(ColumnVirtualizerDynamic) {
    attrs.columns = columns
}
