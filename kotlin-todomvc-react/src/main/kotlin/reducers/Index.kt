package katas.todomvc.reducers

import combineReducers
import katas.todomvc.domain.Todo
import katas.todomvc.domain.Visibility

class State(
    val todos: Array<Todo> = emptyArray(),
    val visibility: Visibility = Visibility.SHOW_ALL
)

fun combinedReducers() = combineReducers(
    mapOf(
        State::todos to ::todosReducer,
        State::visibility to ::visibilityFilterReducer
    )
)
