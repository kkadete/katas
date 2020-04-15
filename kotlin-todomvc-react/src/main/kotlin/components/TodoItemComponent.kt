package katas.todomvc.components

import katas.todomvc.actions.DestroyTodoAction
import katas.todomvc.actions.ToggleTodoAction
import katas.todomvc.domain.Todo
import katas.todomvc.reducers.State
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

class TodoItemComponent(props: ConnectedTodoItemProps) : RComponent<ConnectedTodoItemProps, RState>(props) {

    override fun RState.init(props: ConnectedTodoItemProps) {
        // empty
    }

    private val handleDelete: (Event) -> Unit = {event->
        event.preventDefault()

        props.destroy(props.id)
    }

    private val handleToogle: (Event) -> Unit = {
        props.toggleTodo(props.id)
    }

    override fun RBuilder.render() {
        li {
            div {
                form {
                    input(type = InputType.checkBox, classes = "toggle") {
                        attrs {
                            defaultChecked = props.todo.completed
                            onClickFunction = handleToogle
                        }
                    }
                    label {
                        +props.todo.text
                    }
                    button(classes = "destroy") {
                        attrs {
                            onClickFunction = handleDelete
                        }
                    }
                }
            }
        }
    }

    companion object : RStatics<ConnectedTodoItemProps, RState, TodoItemComponent, Nothing>(TodoItemComponent::class) {
        init {
            getDerivedStateFromProps = { props, state ->
                state
            }
        }
    }
}

interface ConnectedTodoItemProps : OwnTodoItemPros, TodoItemStateProps, TodoItemDispatchProps

interface OwnTodoItemPros : RProps {
    var id: Int
    var todo: Todo
}

interface TodoItemStateProps : RProps

interface TodoItemDispatchProps : RProps {
    var destroy: (id: Int) -> Unit
    var toggleTodo: (Int) -> Unit
}

fun TodoItemStateProps.mapStateToProps(state: State, ownProps: OwnTodoItemPros) {
}

fun TodoItemDispatchProps.mapDispatchToProps(dispatch: (RAction) -> WrapperAction, ownProps: OwnTodoItemPros) {
    destroy = { id: Int -> dispatch(DestroyTodoAction(id)) }
    toggleTodo = { dispatch(ToggleTodoAction(it)) }
}

val TodoItemConnector = rConnect<State, RAction, WrapperAction, OwnTodoItemPros, TodoItemStateProps, TodoItemDispatchProps, ConnectedTodoItemProps>(
    TodoItemStateProps::mapStateToProps,
    TodoItemDispatchProps::mapDispatchToProps
)
val ConnectedTodoItem = TodoItemConnector(TodoItemComponent::class.js.unsafeCast<RClass<ConnectedTodoItemProps>>())

fun RBuilder.todoItemComponent(key: String, id: Int, todo: Todo): ReactElement = ConnectedTodoItem {
    attrs.key = key
    attrs.id = id
    attrs.todo = todo
}
