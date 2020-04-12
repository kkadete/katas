package katas.todomvc.components

import katas.todomvc.container.filterLinkComponent
import katas.todomvc.domain.VisibilityFilter
import react.RBuilder
import react.dom.div
import react.dom.span

fun RBuilder.todoFooterComponent() =
    div("footer") {
        span { +"Show: " }
        filterLinkComponent {
            attrs.filter = VisibilityFilter.SHOW_ALL
            +"All"
        }
        filterLinkComponent {
            attrs.filter = VisibilityFilter.SHOW_ACTIVE
            +"Active"
        }
        filterLinkComponent {
            attrs.filter = VisibilityFilter.SHOW_COMPLETED
            +"Completed"
        }
    }
