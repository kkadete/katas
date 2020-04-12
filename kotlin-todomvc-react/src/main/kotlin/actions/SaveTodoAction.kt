package katas.todomvc.actions

import katas.todomvc.domain.Todo
import redux.RAction

class SaveTodoAction(val id: Todo, val text: String): RAction
