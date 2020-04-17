package katas.todomvc.components

import katas.todomvc.actions.DestroyTodoAction
import katas.todomvc.actions.ToggleTodoAction
import katas.todomvc.domain.Todo
import katas.todomvc.reducers.State
import katas.todomvc.selectors.getTodos
import kotlinx.html.InputType
import kotlinx.html.classes
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onDoubleClickFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.RHandler
import react.RProps
import react.ReactElement
import react.dom.button
import react.dom.div
import react.dom.input
import react.dom.label
import react.dom.li
import react.invoke
import react.key
import react.rFunction
import react.redux.rConnect
import react.useMemo
import react.useState
import redux.RAction
import redux.WrapperAction


val TodoItem = rFunction("TodoItemComponent") { props: ConnectedTodoItemProps ->

    val handleEdit: (todoId: Int) -> Unit = {todoId ->
        props.onEdit(todoId)
    }

    val handleSubmit: (Event) -> Unit = {
        // TODO
    }

    val handleOnChange: (Event) -> Unit = {
        // TODO
    }

    val handleDelete: (todoId: Int) -> Unit = { todoId ->
        props.destroy(todoId)
    }

    val handleToogle: (todoId: Int) -> Unit = {todoId ->
        props.toggleTodo(todoId)
    }

    val handleOnCancel: (todoId: Int) -> Unit = {todoId ->
        props.onCancel()
    }

    var editText = useState(props.todo.title);

    li {
        attrs {
            classes = setOf(if (props.todo.completed) "completed" else "", if (props.editing) "editing" else "")
        }
        div(classes = "view") {
            input(type = InputType.checkBox, classes = "toggle") {
                attrs {
                    checked = props.todo.completed
                    onChangeFunction = useMemo({ { handleToogle(props.todo.id) } }, arrayOf(props.todo.id))
                }
            }
            label {
                attrs {
                    onDoubleClickFunction = useMemo({ { handleEdit(props.todo.id) } }, arrayOf(props.todo.id))
                }
                +props.todo.title
            }
            button(classes = "destroy") {
                attrs {
                    onClickFunction = useMemo({ { handleDelete(props.todo.id) } }, arrayOf(props.todo.id))
                }
            }
        }
        input(type = InputType.text, classes = "edit") {
            attrs {
                value = editText.first
                onSubmitFunction = handleSubmit
                onChangeFunction = handleOnChange
            }
            // ref =
        }
    }
}

interface ConnectedTodoItemProps : OwnTodoItemProps, TodoItemStateProps, TodoItemDispatchProps

interface OwnTodoItemProps : RProps {
    var id: Int
    var editing: Boolean
    var onEdit: (id: Int) -> Unit
    var onSave: (id: Int, title: String) -> Unit
    var onCancel: () -> Unit
}

interface TodoItemStateProps : RProps {
    var todo: Todo
}

interface TodoItemDispatchProps : RProps {
    var destroy: (id: Int) -> Unit
    var toggleTodo: (Int) -> Unit
}

fun TodoItemStateProps.mapStateToProps(state: State, ownProps: OwnTodoItemProps) {
    todo = getTodos(state).first { it.id == ownProps.id }
}

fun TodoItemDispatchProps.mapDispatchToProps(dispatch: (RAction) -> WrapperAction, ownProps: OwnTodoItemProps) {
    destroy = { id: Int -> dispatch(DestroyTodoAction(id)) }
    toggleTodo = { dispatch(ToggleTodoAction(it)) }
}

val TodoItemConnector = rConnect<State, RAction, WrapperAction, OwnTodoItemProps, TodoItemStateProps, TodoItemDispatchProps, ConnectedTodoItemProps>(
    TodoItemStateProps::mapStateToProps,
    TodoItemDispatchProps::mapDispatchToProps
)
val ConnectedTodoItem = TodoItemConnector(TodoItem)

fun RBuilder.todoItemComponent(key: String, id: Int, editing: Boolean, handler: RHandler<OwnTodoItemProps>): ReactElement = ConnectedTodoItem {
    attrs.key = key
    attrs.id = id
    attrs.editing = editing
    handler()
}
