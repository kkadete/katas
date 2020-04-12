package katas.todomvc.actions

import katas.todomvc.domain.Visibility
import redux.RAction

class SetVisibilityFilterAction(val filter: Visibility) : RAction
