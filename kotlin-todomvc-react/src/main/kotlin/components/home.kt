package katas.todomvc.components

import react.RBuilder
import react.dom.div
import react.dom.section

fun RBuilder.home() =
    div {
        section(classes = "todoapp") {
            todoHeaderComponent()
            todoListComponent()
            // TODO: only show footer if there are Todos
            todoFooterComponent()
        }
        footerComponent()
    }
