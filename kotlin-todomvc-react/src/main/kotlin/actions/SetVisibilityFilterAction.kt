package katas.todomvc.actions

import katas.todomvc.domain.VisibilityFilter
import redux.RAction

class SetVisibilityFilterAction(val filter: VisibilityFilter) : RAction
