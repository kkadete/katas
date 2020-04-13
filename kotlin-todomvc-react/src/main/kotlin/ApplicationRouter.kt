package katas.todomvc

import katas.todomvc.components.footerComponent
import katas.todomvc.components.todoFooterComponent
import katas.todomvc.components.todoHeaderComponent
import katas.todomvc.components.todoListComponent
import react.RBuilder
import react.dom.div
import react.dom.section
import react.router.dom.browserRouter
import react.router.dom.route
import react.router.dom.switch

fun RBuilder.applicationRouter() =
    browserRouter {
        switch {
            route("/:filter?") {
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
        }
    }
