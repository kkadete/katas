package katas.todomvc.components

import katas.todomvc.reducers.State
import katas.todomvc.selectors.getTodos
import react.RBuilder
import react.RProps
import react.dom.div
import react.dom.section
import react.invoke
import react.rFunction
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

val Home = rFunction("HomeComponent") { props: ConnectedHomeProps ->
    div {
        section(classes = "todoapp") {
            todoHeaderComponent()
            todoMainComponent()

            if (props.hasTodos) {
                todoFooterComponent()
            }
        }
        footerComponent()
    }
}

interface ConnectedHomeProps : HomeStateProps, HomeDispatchProps, OwnHomeProps

interface OwnHomeProps : RProps {
}

interface HomeStateProps : RProps {
    var hasTodos: Boolean
}

interface HomeDispatchProps : RProps {
}

fun HomeStateProps.mapStateToProps(state: State, ownProps: OwnHomeProps) {
    hasTodos = getTodos(state).isNotEmpty()
}

fun HomeDispatchProps.mapDispatchToProps(dispatch: (RAction) -> WrapperAction, ownProps: OwnHomeProps) {
}

val HomeConnector = rConnect<State, RAction, WrapperAction, OwnHomeProps, HomeStateProps, HomeDispatchProps, ConnectedHomeProps>(
    HomeStateProps::mapStateToProps,
    HomeDispatchProps::mapDispatchToProps
)
val ConnectedHome = HomeConnector(Home)

fun RBuilder.homeComponent() = ConnectedHome {
}
