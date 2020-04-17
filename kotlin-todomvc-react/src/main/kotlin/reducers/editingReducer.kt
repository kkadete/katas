package katas.todomvc.reducers

import katas.todomvc.actions.SetEditingAction
import redux.RAction

fun editingReducer(state: Boolean = false, action: RAction): Boolean = when (action) {
    is SetEditingAction -> action.editing
    else -> state
}
