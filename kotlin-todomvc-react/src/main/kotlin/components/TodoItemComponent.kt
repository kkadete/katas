package katas.todomvc.components

import katas.todomvc.actions.DestroyTodoAction
import katas.todomvc.actions.ToggleTodoAction
import katas.todomvc.domain.Todo
import katas.todomvc.reducers.State
import katas.todomvc.selectors.getTodos
import kotlinx.html.InputType
import kotlinx.html.classes
import kotlinx.html.js.onBlurFunction
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onDoubleClickFunction
import kotlinx.html.js.onKeyDownFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import react.RBuilder
import react.RHandler
import react.RProps
import react.ReactElement
import react.createRef
import react.dom.button
import react.dom.div
import react.dom.input
import react.dom.label
import react.dom.li
import react.invoke
import react.rFunction
import react.redux.rConnect
import react.router.dom.switch
import react.useState
import redux.RAction
import redux.WrapperAction

const val ESCAPE_KEY = 27

val TodoItem = rFunction("TodoItemComponent") { props: ConnectedTodoItemProps ->
    val inputRef = createRef<HTMLInputElement>()

    val (editText, setEditText) = useState("");

    val handleOnChange: (Event) -> Unit = {event ->
        val target = (event.currentTarget as HTMLInputElement)

        setEditText(target.value);
    }

    val handleOnEdit: (Event) -> Unit = {event ->
        props.onEdit(props.todo.id)

        setEditText(props.todo.title)
    }

    val handleOnSubmit: (Event) -> Unit = { event ->
        val value = editText.trim()

        if(!value.isEmpty()){
            props.onSave(props.id, value)

            setEditText(editText)
        } else {
            props.onDestroy(props.id)
        }
    }

    val handleOnKeyDown: (Event) -> Unit = { event ->
        val keyboardEvent = event.unsafeCast<KeyboardEvent>()

        when (keyboardEvent.keyCode) {
            ENTER_KEY -> handleOnSubmit(event)
            ESCAPE_KEY -> {
                setEditText(props.todo.title)

                props.onCancel()
            }
        }
    }

    li {
        attrs {
            classes = setOf(if (props.todo.completed) "completed" else "", if (props.isEditing) "editing" else "")
        }
        div(classes = "view") {
            input(type = InputType.checkBox, classes = "toggle") {
                attrs {
                    checked = props.todo.completed
                    onChangeFunction = { _: Event -> props.onToogle(props.todo.id) }
                }
            }
            label {
                attrs {
                    onDoubleClickFunction = handleOnEdit
                }
                +props.todo.title
            }
            button(classes = "destroy") {
                attrs {
                    onClickFunction = { e: Event -> props.onDestroy(props.todo.id) }
                }
            }
        }
        input(type = InputType.text, classes = "edit") {
            attrs {
                value = editText
                onBlurFunction = handleOnSubmit
                onChangeFunction = handleOnChange
                onKeyDownFunction = handleOnKeyDown
            }
            ref = inputRef
        }
    }
}

interface ConnectedTodoItemProps : OwnTodoItemProps, TodoItemStateProps, TodoItemDispatchProps

interface OwnTodoItemProps : RProps {
    var id: Int
    var isEditing: Boolean
    var onToogle: (id: Int) -> Unit
    var onDestroy: (id: Int) -> Unit
    var onEdit: (id: Int) -> Unit
    var onSave: (id: Int, title: String) -> Unit
    var onCancel: () -> Unit
}

interface TodoItemStateProps : RProps {
    var todo: Todo
}

interface TodoItemDispatchProps : RProps {
    var destroy: (id: Int) -> Unit
}

fun TodoItemStateProps.mapStateToProps(state: State, ownProps: OwnTodoItemProps) {
    todo = getTodos(state).first { it.id == ownProps.id }
}

fun TodoItemDispatchProps.mapDispatchToProps(dispatch: (RAction) -> WrapperAction, ownProps: OwnTodoItemProps) {
    destroy = { id: Int -> dispatch(DestroyTodoAction(id)) }
}

val TodoItemConnector = rConnect<State, RAction, WrapperAction, OwnTodoItemProps, TodoItemStateProps, TodoItemDispatchProps, ConnectedTodoItemProps>(
    TodoItemStateProps::mapStateToProps,
    TodoItemDispatchProps::mapDispatchToProps
)
val ConnectedTodoItem = TodoItemConnector(TodoItem)

fun RBuilder.todoItemComponent(handler: RHandler<OwnTodoItemProps>): ReactElement = ConnectedTodoItem {
    handler()
}
