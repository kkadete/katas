package katas.todomvc

import katas.todomvc.reducers.State
import katas.todomvc.reducers.combinedReducers
import kotlinext.js.require
import kotlinext.js.requireAll
import react.dom.render
import react.redux.provider
import redux.RAction
import redux.compose
import redux.createStore
import redux.rEnhancer
import kotlin.browser.document

val store = createStore<State, RAction, dynamic>(
    combinedReducers(),
    State(),
    compose(rEnhancer(), js("if(window.__REDUX_DEVTOOLS_EXTENSION__ )window.__REDUX_DEVTOOLS_EXTENSION__ ();else(function(f){return f;});"))
)

fun main() {
    // https://youtrack.jetbrains.com/issue/KT-32721
    requireAll(require.context("../../../../processedResources/js/main/", true, js("/\\.css$/")))

    val rootDiv = document.getElementById("root")
    render(rootDiv) {
        provider(store) {
            applicationRouter()
        }
    }
}
