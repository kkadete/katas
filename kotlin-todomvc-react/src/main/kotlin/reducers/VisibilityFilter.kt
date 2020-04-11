package katas.todomvc.reducers

import katas.todomvc.actions.SetVisibilityFilterAction
import katas.todomvc.domain.VisibilityFilter
import redux.RAction

fun visibilityFilterReducer(state: VisibilityFilter = VisibilityFilter.SHOW_ALL, action: RAction): VisibilityFilter = when (action) {
    is SetVisibilityFilterAction -> action.filter
    else -> state
}
