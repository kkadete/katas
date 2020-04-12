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

interface TodoHeaderProps: OwnTodoHeaderStateProps, TodoHeaderStateProps, TodoHeaderDispatchProps

class TodoHeaderComponent(props: TodoHeaderProps) : RComponent<TodoHeaderProps, RState>(props) {
    private val inputRef = createRef<HTMLInputElement>()

    override fun RState.init(props: TodoHeaderProps) {
        // empty
    }

    private val handleInput: (Event) -> Unit = { event ->
        event.preventDefault()

        inputRef.current!!.let {
            if (it.value.trim().isNotEmpty()) {
                props.addTodo(it.value)
                it.value = ""
            }
        }
    }

    override fun RBuilder.render() {
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
}

interface OwnTodoHeaderStateProps : RProps

interface TodoHeaderStateProps : RProps

interface TodoHeaderDispatchProps : RProps {
    var addTodo: (text: String) -> Unit
}

fun TodoHeaderStateProps.mapStateToProps(state: State, ownProps: OwnTodoHeaderStateProps) {
}

fun TodoHeaderDispatchProps.mapDispatchToProps(dispatch: (RAction) -> WrapperAction, ownProps: OwnTodoHeaderStateProps) {
    addTodo = {text: String -> dispatch(AddTodoAction(text))}
}

val todoHeaderComponent: RClass<OwnTodoHeaderStateProps> = rConnect<State, RAction, WrapperAction, OwnTodoHeaderStateProps, TodoHeaderStateProps, TodoHeaderDispatchProps, TodoHeaderProps>(
    TodoHeaderStateProps::mapStateToProps,
    TodoHeaderDispatchProps::mapDispatchToProps
)(TodoHeaderComponent::class.js.unsafeCast<RClass<TodoHeaderProps>>())
