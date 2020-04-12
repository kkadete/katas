package katas.todomvc.components

import katas.todomvc.actions.SetVisibilityFilterAction
import katas.todomvc.domain.Visibility
import katas.todomvc.reducers.State
import react.*
import react.dom.li
import react.redux.rConnect
import react.router.dom.navLink
import redux.RAction
import redux.WrapperAction

interface LinkProps : LinkStateProps, LinkDispatchProps, OwnLinkProps

class LinkComponent(props: LinkProps) : RComponent<LinkProps, RState>(props) {

    override fun RState.init(props: LinkProps) {
        // empty
    }

    override fun RBuilder.render() {
        li {
            navLink(
                to = if (props.filter == Visibility.SHOW_ALL) "" else "/${props.filter}",
                className = if (props.active) "selected" else "",
                exact = true,
                activeClassName = "selected") {
                children()
            }
        }
    }
}

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
    active = state.visibility == ownProps.filter
}

fun LinkDispatchProps.mapDispatchToProps(dispatch: (RAction) -> WrapperAction, ownProps: OwnLinkProps) {
    setVisibilityFilter = { dispatch(SetVisibilityFilterAction(ownProps.filter)) }
}

val linkComponent: RClass<OwnLinkProps> = rConnect<State, RAction, WrapperAction, OwnLinkProps, LinkStateProps, LinkDispatchProps, LinkProps>(
    LinkStateProps::mapStateToProps,
    LinkDispatchProps::mapDispatchToProps
)(LinkComponent::class.js.unsafeCast<RClass<LinkProps>>())
