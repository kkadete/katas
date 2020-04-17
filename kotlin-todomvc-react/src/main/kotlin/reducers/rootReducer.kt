package katas.todomvc.reducers

import redux.RAction

fun rootReducer(state: State, action: RAction): State = state.copy(
    todos = todosReducer(state.todos, action),
    visibility = visibilityReducer(state.visibility, action),
    editing = editingReducer(state.editing, action)
)
