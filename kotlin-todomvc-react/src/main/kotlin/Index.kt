package katas.todomvc

import katas.todomvc.components.ApplicationComponent
import kotlinext.js.requireAll
import kotlinext.js.require
import react.dom.render
import kotlin.browser.document

fun main() {
    // https://youtrack.jetbrains.com/issue/KT-32721
    requireAll(require.context("../../../../processedResources/js/main/", true, js("/\\.css$/")))

    render(document.getElementById("root")) {
        child(ApplicationComponent::class) {}
    }
}
