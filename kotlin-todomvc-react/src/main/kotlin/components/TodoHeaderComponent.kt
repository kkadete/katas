package katas.todomvc.components

import katas.todomvc.actions.AddTodoAction
import katas.todomvc.reducers.State
import kotlinx.html.InputType
import kotlinx.html.js.onKeyDownFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import react.RBuilder
import react.RProps
import react.createRef
import react.dom.h1
import react.dom.header
import react.dom.input
import react.invoke
import react.rFunction
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

const val ENTER_KEY = 13;

val TodoHeader = rFunction("TodoHeaderComponent") { props: ConnectedTodoHeaderProps ->
    val inputRef = createRef<HTMLInputElement>()

    val submitFunction: (event: Event) -> Unit = { event ->
        event.preventDefault()

        inputRef.current!!.let {
            if (it.value.trim().isNotEmpty()) {
                props.addTodo(it.value)
                it.value = ""
            }
        }
    }

    val handleNewTodoKeyDown: (Event) -> Unit = { event ->
        val keyboardEvent = event.unsafeCast<KeyboardEvent>()

        if (keyboardEvent.keyCode == ENTER_KEY) {
            submitFunction(event)
        }
    }

    header("header") {
        h1 {
            +"todos"
        }
        input(type = InputType.text, classes = "new-todo") {
            attrs {
                placeholder = "What needs to be done?"
                autoFocus = true
                onKeyDownFunction = handleNewTodoKeyDown
            }
            ref = inputRef
        }
    }
}

interface ConnectedTodoHeaderProps : OwnTodoHeaderStateProps, TodoHeaderStateProps, TodoHeaderDispatchProps

interface OwnTodoHeaderStateProps : RProps

interface TodoHeaderStateProps : RProps

interface TodoHeaderDispatchProps : RProps {
    var addTodo: (text: String) -> Unit
}

fun TodoHeaderStateProps.mapStateToProps(state: State, ownProps: OwnTodoHeaderStateProps) {
}

fun TodoHeaderDispatchProps.mapDispatchToProps(dispatch: (RAction) -> WrapperAction, ownProps: OwnTodoHeaderStateProps) {
    addTodo = { text: String -> dispatch(AddTodoAction(text)) }
}

val TodoHeaderConnector = rConnect<State, RAction, WrapperAction, OwnTodoHeaderStateProps, TodoHeaderStateProps, TodoHeaderDispatchProps, ConnectedTodoHeaderProps>(
    TodoHeaderStateProps::mapStateToProps,
    TodoHeaderDispatchProps::mapDispatchToProps
)
val ConnectedTodoHeader = TodoHeaderConnector(TodoHeader)

fun RBuilder.todoHeaderComponent() = ConnectedTodoHeader {}
