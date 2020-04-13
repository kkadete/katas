package katas.todomvc.components

import react.RBuilder
import react.RProps
import react.dom.a
import react.dom.footer
import react.dom.p
import react.rFunction

val FooterComponent = rFunction("FooterComponent") { _: RProps ->
    footer("info") {
        p { +"Double-click to edit a todo" }
        p {
            +"Created by"
            +" "
            a("https://github.com/kkadete") { +"kkadete" }
        }
        p {
            +"Based on "
            a("http://todomvc.com") {
                +"TodoMVC"
            }
        }
    }
}

fun RBuilder.footerComponent() = FooterComponent {}
