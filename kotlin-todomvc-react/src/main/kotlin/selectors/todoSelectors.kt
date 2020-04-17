package katas.todomvc.selectors

import katas.todomvc.reducers.State

fun getTodos(state: State) = state.todos

fun getVisibility(state: State) = state.visibility

fun isEditing(state: State) = state.editing
