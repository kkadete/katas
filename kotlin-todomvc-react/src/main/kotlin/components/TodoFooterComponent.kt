package katas.todomvc.components

import katas.todomvc.actions.ClearCompletedTodosAction
import katas.todomvc.domain.Visibility
import katas.todomvc.reducers.State
import katas.todomvc.selectors.getTodos
import katas.todomvc.selectors.getVisibility
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.RProps
import react.dom.*
import react.rFunction
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction
import react.*

val TodoFooter = rFunction("TodoFooterComponent") { props: ConnectedTodoFooterProps ->
    val handleClearCompleted: (Event) -> Unit = {
        props.clearCompleted()
    }

    footer("footer") {
        span("todo-count") {
            strong {
                +"${props.count}"
            }
            +" "
            +pluralize(props.count, "item")
            +" left"
        }
        ul("filters") {
            linkComponent {
                attrs {
                    filter = Visibility.SHOW_ALL
                }
                +"All"
            }
            linkComponent {
                attrs {
                    filter = Visibility.SHOW_ACTIVE
                }
                +"Active"
            }
            linkComponent {
                attrs {
                    filter = Visibility.SHOW_COMPLETED
                }
                +"Completed"
            }
        }
        if (props.completedCount > 0) {
            button(classes = "clear-completed") {
                attrs {
                    onClickFunction = handleClearCompleted
                }
                +"Clear completed"
            }
        }
    }
}

private fun pluralize(count: Int, word: String): String {
    return if (count == 0) word else word + "s"
}

interface ConnectedTodoFooterProps : OwnTodoFooterProps, TodoFooterStateProps, TodoFooterDispatchProps

interface OwnTodoFooterProps : RProps

interface TodoFooterStateProps : RProps {
    var count: Int
    var completedCount: Int
}

interface TodoFooterDispatchProps : RProps {
    var clearCompleted: () -> Unit
}

fun TodoFooterStateProps.mapStateToProps(state: State, ownProps: OwnTodoFooterProps) {
    count = getTodos(state).size
    completedCount = getTodos(state).filter { it.completed }.count()
}

fun TodoFooterDispatchProps.mapDispatchToProps(dispatch: (RAction) -> WrapperAction, ownProps: OwnTodoFooterProps) {
    clearCompleted = { dispatch(ClearCompletedTodosAction()) }
}

val TodoFooterConnector = rConnect<State, RAction, WrapperAction, OwnTodoFooterProps, TodoFooterStateProps, TodoFooterDispatchProps, ConnectedTodoFooterProps>(
    TodoFooterStateProps::mapStateToProps,
    TodoFooterDispatchProps::mapDispatchToProps
)

val ConnectedTodoFooter = TodoFooterConnector(TodoFooter)

fun RBuilder.todoFooterComponent() = ConnectedTodoFooter {}
