package katas.todomvc.components

import katas.todomvc.container.headerComponent
import katas.todomvc.container.visibleTodoListComponent
import react.RBuilder
import react.dom.br
import react.dom.div
import react.dom.h1
import react.router.dom.browserRouter
import react.router.dom.navLink
import react.router.dom.route
import react.router.dom.switch

private const val TODO_LIST_PATH = "/todolist"

fun RBuilder.application() =
    browserRouter {
        switch {
            route("/", exact = true) {
                div {
                    h1 {
                        +"Kotlin React + React-Dom + Redux + React-Redux + React-Router Example"
                    }
                    navLink(TODO_LIST_PATH) {
                        +"Go to todo list"
                    }
                }
            }
            route(TODO_LIST_PATH) {
                div {
                    headerComponent {}
                    visibleTodoListComponent {}
                    footer()
                    infoComponent()
                    br {}
                    navLink("/") {
                        +"Go back"
                    }
                }
            }
        }
    }
