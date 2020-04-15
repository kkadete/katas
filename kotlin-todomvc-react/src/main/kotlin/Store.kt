package katas.todomvc

import katas.todomvc.reducers.State
import katas.todomvc.reducers.rootReducer
import katas.todomvc.redux.EmptyMiddleware
import redux.*
import kotlin.browser.window

val INITIAL_STATE = State()

private fun <A, T1, R> composeWithDevTools(function1: (T1) -> R, function2: (A) -> T1): (A) -> R {
    val reduxDevtoolsExtensionCompose = window.asDynamic().__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
    if (reduxDevtoolsExtensionCompose == undefined) {
        return compose(function1, function2)
    }
    return reduxDevtoolsExtensionCompose(function1, function2) as Function1<A, R>
}

fun configureStore(
    emptyMiddleware: EmptyMiddleware<State, RAction, WrapperAction>,
    initialState: State = INITIAL_STATE): Store<State, RAction, WrapperAction> {
    val emptyEnhancer = applyMiddleware(emptyMiddleware.createMiddleware())

    return createStore(::rootReducer, initialState, composeWithDevTools(emptyEnhancer, rEnhancer()))
}

fun initialiseStore(emptyMiddleware: EmptyMiddleware<State, RAction, WrapperAction>): Store<State, RAction, WrapperAction> {
    return configureStore(emptyMiddleware)
}


