package katas.todomvc.reducers

import katas.todomvc.actions.AddTodoAction
import katas.todomvc.actions.ToggleTodoAction
import katas.todomvc.domain.Todo
import redux.RAction

fun todosReducer(state: Array<Todo> = emptyArray(), action: RAction): Array<Todo> = when (action) {
    is AddTodoAction -> state + Todo(action.id, action.text, false)
    is ToggleTodoAction -> state.map {
        if (it.id == action.id) {
            it.copy(completed = !it.completed)
        } else {
            it
        }
    }.toTypedArray()
    else -> state
}
