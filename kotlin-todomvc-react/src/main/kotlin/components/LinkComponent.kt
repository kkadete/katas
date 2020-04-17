package katas.todomvc.components

import katas.todomvc.actions.SetVisibilityFilterAction
import katas.todomvc.domain.Visibility
import katas.todomvc.reducers.State
import katas.todomvc.selectors.getVisibility
import react.*
import react.dom.li
import react.redux.rConnect
import react.router.dom.navLink
import redux.RAction
import redux.WrapperAction

val Link = rFunction("LinkComponent") { props: ConnectedLinkProps ->
    li {
        navLink(
            to = if (props.filter == Visibility.SHOW_ALL) "" else "/${props.filter}",
            className = if (props.active) "selected" else "",
            exact = true,
            activeClassName = "selected") {
            props.children()
        }
    }
}

interface ConnectedLinkProps : LinkStateProps, LinkDispatchProps, OwnLinkProps

interface OwnLinkProps : RProps {
    var filter: Visibility
}

interface LinkStateProps : RProps {
    var active: Boolean
}

interface LinkDispatchProps : RProps {
    var setVisibilityFilter: () -> Unit
}

fun LinkStateProps.mapStateToProps(state: State, ownProps: OwnLinkProps) {
    active = getVisibility(state) == ownProps.filter
}

fun LinkDispatchProps.mapDispatchToProps(dispatch: (RAction) -> WrapperAction, ownProps: OwnLinkProps) {
    setVisibilityFilter = { dispatch(SetVisibilityFilterAction(ownProps.filter)) }
}

val LinkConnector = rConnect<State, RAction, WrapperAction, OwnLinkProps, LinkStateProps, LinkDispatchProps, ConnectedLinkProps>(
    LinkStateProps::mapStateToProps,
    LinkDispatchProps::mapDispatchToProps
)
val ConnectedLink = LinkConnector(Link)

fun RBuilder.linkComponent(handler: RHandler<OwnLinkProps>) = ConnectedLink {
    handler()
}
