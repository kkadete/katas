package katas.todomvc.reducers

import katas.todomvc.actions.*
import katas.todomvc.domain.Todo
import redux.RAction

fun todosReducer(state: Array<Todo> = arrayOf(), action: RAction): Array<Todo> = when (action) {
    is AddTodoAction -> {
        state + Todo(action.id, action.text, false)
    }
    is ToggleTodoAction -> {
        state.map {
            if (it.id == action.id) {
                it.completed = !it.completed
                it
            } else {
                it
            }
        }.toTypedArray()
    }
    is ClearCompletedTodosAction -> {
        state.filter { !it.completed }.toTypedArray()
    }
    is DestroyTodoAction -> {
        state.filter { it.id != action.id }.toTypedArray()
    }
    is ToggleAllTodosAction -> {
        state.map {
            it.apply { completed = action.checked}
        }.toTypedArray()
    }
    is SaveTodoAction ->{
        state.map {
            if(it.id == action.id){
                it.copy(title = action.text)
            } else {
                it.copy()
            }
        }.toTypedArray()
    }
    else -> state
}
