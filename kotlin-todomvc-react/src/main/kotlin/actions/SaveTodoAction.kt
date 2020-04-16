package katas.todomvc.actions

import redux.RAction

class SaveTodoAction(val id: Int, val text: String) : RAction
