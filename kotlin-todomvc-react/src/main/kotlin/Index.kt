package katas.todomvc

import katas.todomvc.reducers.State
import katas.todomvc.redux.EmptyMiddleware
import kotlinext.js.require
import kotlinext.js.requireAll
import react.dom.render
import react.redux.provider
import redux.RAction
import redux.Store
import redux.WrapperAction
import kotlin.browser.document

private fun initialiseRedux(): Store<State, RAction, WrapperAction> {
    val emptyMiddleware = EmptyMiddleware<State, RAction, WrapperAction>()

    return initialiseStore(emptyMiddleware)
}

fun main() {
    // https://youtrack.jetbrains.com/issue/KT-32721
    requireAll(require.context("../../../../processedResources/js/main/", true, js("/\\.css$/")))

    val store = initialiseRedux()

    val rootDiv = document.getElementById("root")
    render(rootDiv) {
        provider(store) {
            application()
        }
    }
}
