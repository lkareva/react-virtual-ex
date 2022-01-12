import kotlinx.css.*
import kotlinx.css.properties.translateX
import kotlinx.css.properties.translateY
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.attrs
import react.dom.button
import react.virtual.ScrollToIndexOptions
import react.virtual.useVirtual
import styled.css
import styled.styledDiv

fun ScrollToIndexOptions(): ScrollToIndexOptions = js("{}")

external interface GridVirtualizerDynamicProps : Props {
    var rows: Array<Int>
    var columns: Array<Int>
}
val GridVirtualizerDynamic = fc("GridVirtualizerDynamic") { props: GridVirtualizerDynamicProps ->
    val parenttRef = useRef<Any>()
    val rowVirtualizer = useVirtual(
        options = VirtualOptions().apply {
            size = props.rows.size
            parentRef = parenttRef
        }
    )
    val columnVirtualizer = useVirtual(
        options = VirtualOptions().apply {
            horizontal = true
            size = props.columns.size
            parentRef = parenttRef
        }
    )
    val (show, setShow) = useState(true)
    val halfWay = rows.size / 2
    button {
        +"Toggle"
        attrs.onClickFunction = {
            setShow { old ->
                !old
            }
        }
    }
    button {
        +" Scroll to index $halfWay"
        attrs.onClickFunction = {rowVirtualizer.scrollToIndex(halfWay,ScrollToIndexOptions())}
    }
    button {
        +" Scroll to index ${props.rows.size - 1}"
        attrs.onClickFunction = {rowVirtualizer.scrollToIndex(props.rows.size - 1,ScrollToIndexOptions())}
    }
    if(show) {
        styledDiv {
            ref = parenttRef
            attrs {
                id = "List"
            }
            css {
                height = 400.px
                width = 500.px
                overflow = Overflow.auto
            }
            styledDiv {
                css {
                    height = rowVirtualizer.totalSize.px
                    width = columnVirtualizer.totalSize.px
                    position = Position.relative
                }
                rowVirtualizer.virtualItems.map { virtualRow ->
                    Fragment{
                        key = "${virtualRow.index}"
                        ref = virtualRow.measureRef
                        columnVirtualizer.virtualItems.map { virtualColumn ->
                            styledDiv {
                                attrs.id = if (virtualColumn.index % 2 == 0) {
                                        if( virtualRow.index % 2 == 0)
                                            "ListItemOdd"
                                        else "ListItemEven"
                                    }
                                    else{
                                        if( virtualRow.index % 2 == 0)
                                            "ListItemEven"
                                        else "ListItemOdd"
                                    }
                                css {
                                    position = Position.absolute
                                    top = 0.px
                                    left = 0.px
                                    height = virtualRow.size.px
                                    width = virtualColumn.size.px
                                    transform.translateX(virtualColumn.start.px)
                                    transform.translateY(virtualRow.start.px)
                                }
                                key = "${virtualColumn.index}"
                                ref = virtualColumn.measureRef
                                +"Cell ${virtualRow.index}, ${virtualColumn.index}"
                            }
                        }
                    }
                }
            }
        }
    }
}
fun RBuilder.gridVirtualizerDynamic(
    rows: Array<Int>,
    columns: Array<Int>
)= child(GridVirtualizerDynamic) {
    attrs.rows = rows
    attrs.columns = columns
}