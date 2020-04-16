package katas.todomvc.components

import react.RBuilder
import react.RProps
import react.dom.div
import react.dom.section
import react.rFunction

val Home = rFunction("HomeComponent") { _: RProps ->
    div {
        section(classes = "todoapp") {
            todoHeaderComponent()
            todoListComponent()
            // TODO: only show footer if there are Todos
            todoFooterComponent()
        }
        footerComponent()
    }
}

fun RBuilder.homeComponent() = Home {}
