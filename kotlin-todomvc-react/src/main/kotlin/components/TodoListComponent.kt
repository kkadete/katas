package katas.todomvc.components

import katas.todomvc.actions.ToggleAllTodosAction
import katas.todomvc.domain.Todo
import katas.todomvc.domain.Visibility
import katas.todomvc.reducers.State
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.input
import react.dom.label
import react.dom.section
import react.dom.ul
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

val TodoList = rFunction("TodoListComponent") { props: ConnectedTodoListProps ->
    val inputRef = createRef<HTMLInputElement>()

    val handleToogleAll: (Event) -> Unit = { event ->
        val target = (event.currentTarget as HTMLInputElement)
        val isChecked = target.checked

        props.toogleAll(isChecked)
    }
    section("main") {
        input(type = InputType.checkBox, classes = "toggle-all") {
            attrs {
                id = "toggle-all"
                onClickFunction = handleToogleAll
            }
            ref = inputRef
        }
        label {
            attrs {
                // TODO: fix chrome warning
                htmlFor = "toggle-all"
            }
            +"Mark all as complete"
        }
        ul("todo-list") {
            props.todos.forEach {
                todoItemComponent(key = "${it.id}", id = it.id, todo = it)
            }
        }
    }
}

interface ConnectedTodoListProps : OwnTodoListPros, TodoListStateProps, TodoListDispatchProps

interface OwnTodoListPros : RProps

interface TodoListStateProps : RProps {
    var todos: Array<Todo>
    var activeTodoCount: Int
}

interface TodoListDispatchProps : RProps {
    var toogleAll: (checked: Boolean) -> Unit
}

fun TodoListStateProps.mapStateToProps(state: State, ownProps: OwnTodoListPros) {
    todos = getVisibleTodos(state.todos, state.visibility)
    activeTodoCount = getVisibleTodos(state.todos, Visibility.SHOW_ACTIVE).size
}

private fun getVisibleTodos(todos: Array<Todo>, filter: Visibility): Array<Todo> = when (filter) {
    Visibility.SHOW_ALL -> todos
    Visibility.SHOW_ACTIVE -> todos.filter { !it.completed }.toTypedArray()
    Visibility.SHOW_COMPLETED -> todos.filter { it.completed }.toTypedArray()
}

fun TodoListDispatchProps.mapDispatchToProps(dispatch: (RAction) -> WrapperAction, ownProps: OwnTodoListPros) {
    toogleAll = { checked -> dispatch(ToggleAllTodosAction(checked)) }
}

val TodoListConnector = rConnect<State, RAction, WrapperAction, OwnTodoListPros, TodoListStateProps, TodoListDispatchProps, ConnectedTodoListProps>(
    TodoListStateProps::mapStateToProps,
    TodoListDispatchProps::mapDispatchToProps
)
val ConnectedTodoList = TodoListConnector(TodoList)

fun RBuilder.todoListComponent() = ConnectedTodoList {}
