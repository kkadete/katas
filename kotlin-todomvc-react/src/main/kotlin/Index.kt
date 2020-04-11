package katas.todomvc

import katas.todomvc.components.Application
import react.dom.render
import kotlin.browser.document

fun main() {
    render(document.getElementById("root")) {
        child(Application::class) {}
    }
}
