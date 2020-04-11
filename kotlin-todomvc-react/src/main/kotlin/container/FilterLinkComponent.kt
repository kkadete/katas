package katas.todomvc.container

import katas.todomvc.actions.SetVisibilityFilterAction
import katas.todomvc.components.LinkComponent
import katas.todomvc.domain.VisibilityFilter
import katas.todomvc.reducers.State
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import react.router.dom.LinkProps
import redux.WrapperAction

interface FilterLinkProps : RProps {
    var filter: VisibilityFilter
}

private interface LinkStateProps : RProps {
    var active: Boolean
}

private interface LinkDispatchProps : RProps {
    var onClick: () -> Unit
}

val filterLinkComponent: RClass<FilterLinkProps> = rConnect<State, SetVisibilityFilterAction, WrapperAction, FilterLinkProps, LinkStateProps, LinkDispatchProps, LinkProps>(
        { state, ownProps ->
            active = state.visibilityFilter == ownProps.filter
        },
        { dispatch, ownProps ->
            onClick = { dispatch(SetVisibilityFilterAction(ownProps.filter)) }
        }
    )(LinkComponent::class.js.unsafeCast<RClass<LinkProps>>())
