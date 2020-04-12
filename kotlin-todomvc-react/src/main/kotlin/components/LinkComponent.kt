package katas.todomvc.components

import katas.todomvc.actions.SetVisibilityFilterAction
import katas.todomvc.domain.Visibility
import katas.todomvc.reducers.State
import kotlinx.html.classes
import react.*
import react.dom.a
import react.dom.li
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

interface LinkProps : LinkStateProps, LinkDispatchProps, OwnLinkProps

class LinkComponent(props: LinkProps) : RComponent<LinkProps, RState>(props) {
    override fun RBuilder.render() {
        li {
            a {
                attrs {
                    classes = if (props.active) setOf("selected") else emptySet()
                    href = "#/" + props.filter
                    +props.title
                }
            }
        }
    }
}

interface OwnLinkProps : RProps {
    var filter: Visibility
    var title: String
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
