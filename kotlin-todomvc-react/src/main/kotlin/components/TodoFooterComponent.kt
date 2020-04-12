package katas.todomvc.components

import katas.todomvc.actions.ClearCompletedTodosAction
import katas.todomvc.domain.Visibility
import katas.todomvc.reducers.State
import kotlinx.html.js.onClickFunction
import kotlinx.html.onClick
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

interface TodoFooterProps : OwnTodoFooterProps, TodoFooterStateProps, TodoFooterDispatchProps

class TodoFooterComponent(props: TodoFooterProps) : RComponent<TodoFooterProps, RState>(props) {

    private val handleClearCompleted: (Event) -> Unit = {
        props.clearCompleted()
    }

    override fun RBuilder.render() {
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
                        title = "All"
                    }
                }
                linkComponent {
                    attrs {
                        filter = Visibility.SHOW_ACTIVE
                        title = "Active"
                    }
                }
                linkComponent {
                    attrs {
                        filter = Visibility.SHOW_COMPLETED
                        title = "Completed"
                    }
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
}

interface OwnTodoFooterProps : RProps

interface TodoFooterStateProps : RProps {
    var count: Int
    var completedCount: Int
}

interface TodoFooterDispatchProps : RProps {
    var clearCompleted: () -> Unit
}

fun TodoFooterStateProps.mapStateToProps(state: State, ownProps: OwnTodoFooterProps) {
    count = state.todos.size
    completedCount = state.todos.filter { it.completed }.count()
}

fun TodoFooterDispatchProps.mapDispatchToProps(dispatch: (RAction) -> WrapperAction, ownProps: OwnTodoFooterProps) {
    clearCompleted = { dispatch(ClearCompletedTodosAction())}
}

val todoFooterComponent: RClass<OwnTodoFooterProps> = rConnect<State, RAction, WrapperAction, OwnTodoFooterProps, TodoFooterStateProps, TodoFooterDispatchProps, TodoFooterProps>(
    TodoFooterStateProps::mapStateToProps,
    TodoFooterDispatchProps::mapDispatchToProps
)(TodoFooterComponent::class.js.unsafeCast<RClass<TodoFooterProps>>())
