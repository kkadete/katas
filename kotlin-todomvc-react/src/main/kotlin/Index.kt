package katas.todomvc

import katas.todomvc.components.ApplicationComponent
import react.dom.render
import kotlin.browser.document

fun main() {
    render(document.getElementById("root")) {
        child(ApplicationComponent::class) {}
    }
}
