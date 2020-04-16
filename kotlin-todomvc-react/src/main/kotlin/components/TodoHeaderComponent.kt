package katas.todomvc.components

import katas.todomvc.actions.AddTodoAction
import katas.todomvc.reducers.State
import kotlinx.html.InputType
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.form
import react.dom.h1
import react.dom.header
import react.dom.input
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

val TodoHeader = rFunction("TodoHeaderComponent") { props: ConnectedTodoHeaderProps ->
    val inputRef = createRef<HTMLInputElement>()

    val handleInput: (Event) -> Unit = { event ->
        event.preventDefault()

        inputRef.current!!.let {
            if (it.value.trim().isNotEmpty()) {
                props.addTodo(it.value)
                it.value = ""
            }
        }
    }

    header("header") {
        h1 {
            +"todos"
        }
        form {
            attrs {
                onSubmitFunction = handleInput
            }
            input(type = InputType.text, classes = "new-todo") {
                attrs {
                    placeholder = "What needs to be done?"
                    autoFocus = true
                }
                ref = inputRef
            }
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
