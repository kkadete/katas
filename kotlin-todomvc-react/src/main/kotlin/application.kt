package katas.todomvc

import katas.todomvc.components.homeComponent
import react.RBuilder
import react.router.dom.browserRouter
import react.router.dom.route
import react.router.dom.switch

fun RBuilder.application() =
    browserRouter {
        switch {
            route("/:filter?") {
                homeComponent()
            }
        }
    }
