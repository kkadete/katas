package katas.todomvc.components

import react.RBuilder
import react.dom.a
import react.dom.footer
import react.dom.p

fun RBuilder.footerComponent() =
    footer("info") {
        p { +"Double-click to edit a todo" }
        p {
            +"Created by"
            +" "
            a("https://github.com/kkadete") { +"kkadete" }
        }
        p {
            +"Based on "
            a("http://todomvc.com"){
                +"TodoMVC"
            }
        }
    }
