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

interface TodoItemProps : OwnTodoItemPros, TodoItemStateProps, TodoItemDispatchProps

class TodoItemComponent(props: TodoItemProps) : RComponent<TodoItemProps, RState>(props) {

    override fun RState.init(props: TodoItemProps) {
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
}

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

val ConnectedTodoItemComponent: RClass<OwnTodoItemPros> = rConnect<State, RAction, WrapperAction, OwnTodoItemPros, TodoItemStateProps, TodoItemDispatchProps, TodoItemProps>(
    TodoItemStateProps::mapStateToProps,
    TodoItemDispatchProps::mapDispatchToProps
)(TodoItemComponent::class.js.unsafeCast<RClass<TodoItemProps>>())

fun RBuilder.todoItemComponent(key: String, id: Int, todo: Todo): ReactElement = ConnectedTodoItemComponent {
    attrs.key = key
    attrs.id = id
    attrs.todo = todo
}
