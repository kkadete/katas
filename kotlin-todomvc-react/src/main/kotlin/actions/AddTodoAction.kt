package katas.todomvc.actions

import redux.RAction

class AddTodoAction(val text: String): RAction {
    private companion object {
        var nextTodoId = 1
    }
    val id = nextTodoId++
}
