package katas.todomvc.reducers

import combineReducers
import katas.todomvc.domain.Todo
import katas.todomvc.domain.VisibilityFilter

data class State(
    val todos: Array<Todo> = emptyArray(),
    val visibilityFilter: VisibilityFilter = VisibilityFilter.SHOW_ALL
)

fun combinedReducers() = combineReducers(
    mapOf(
        State::todos to ::todosReducer,
        State::visibilityFilter to ::visibilityFilterReducer
    )
)
