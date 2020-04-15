package katas.todomvc.reducers

import katas.todomvc.actions.SetVisibilityFilterAction
import katas.todomvc.domain.Visibility
import redux.RAction

fun visibilityReducer(state: Visibility = Visibility.SHOW_ALL, action: RAction): Visibility = when (action) {
    is SetVisibilityFilterAction -> action.filter
    else -> state
}
