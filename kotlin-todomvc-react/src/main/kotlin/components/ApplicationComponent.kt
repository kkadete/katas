package katas.todomvc.components

import react.RBuilder
import react.dom.div
import react.dom.section
import react.router.dom.browserRouter
import react.router.dom.route
import react.router.dom.switch

fun RBuilder.application() =
    browserRouter {
        switch {
            // TODO: read #/:filter and update redux
            route("/") {
                div {
                    section(classes = "todoapp") {
                        todoHeaderComponent {}
                        todoListComponent {}
                        // TODO: only show footer if there are Todos
                        todoFooterComponent {}
                    }
                    footerComponent()
                }
            }
        }
    }
