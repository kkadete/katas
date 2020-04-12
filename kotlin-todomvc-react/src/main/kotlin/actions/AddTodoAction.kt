package katas.todomvc.actions

import redux.RAction

class AddTodoAction(val text: String): RAction {
    val id = nextTodoId++

    private companion object {
        var nextTodoId = 1
    }
}
