package katas.todomvc.components

import katas.todomvc.container.headerComponent
import katas.todomvc.container.visibleTodoListComponent
import react.RBuilder
import react.dom.div
import react.router.dom.browserRouter
import react.router.dom.route
import react.router.dom.switch

private const val TODO_PATH = "/"

fun RBuilder.application() =
    browserRouter {
        switch {
            route(TODO_PATH) {
                div(classes = "application") {
                    headerComponent {}
                    visibleTodoListComponent {}
                    todoFooterComponent()
                    infoComponent()
                }
            }
        }
    }
