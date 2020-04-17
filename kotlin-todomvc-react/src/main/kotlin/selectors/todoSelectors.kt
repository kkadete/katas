package katas.todomvc.selectors

import katas.todomvc.domain.Todo
import katas.todomvc.domain.Visibility
import katas.todomvc.reducers.State

fun getTodos(state: State) = state.todos

fun getVisibility(state: State) = state.visibility

fun isEditing(state: State) = state.editing

fun getVisibleTodos(todos: Array<Todo>, filter: Visibility): Array<Todo> = when (filter) {
    Visibility.SHOW_ALL -> todos
    Visibility.SHOW_ACTIVE -> todos.filter { !it.completed }.toTypedArray()
    Visibility.SHOW_COMPLETED -> todos.filter { it.completed }.toTypedArray()
}
