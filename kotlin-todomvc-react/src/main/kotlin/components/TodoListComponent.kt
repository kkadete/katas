package katas.todomvc.components

import katas.todomvc.actions.SaveTodoAction
import katas.todomvc.actions.ToggleAllTodosAction
import katas.todomvc.domain.Todo
import katas.todomvc.domain.Visibility
import katas.todomvc.reducers.State
import katas.todomvc.selectors.getVisibleTodos
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

    val (editing, setEditing) = useState(-1)

    fun handleToogleAll(event: Event): Unit {
        val target = (event.currentTarget as HTMLInputElement)
        val isChecked = target.checked

        props.toogleAll(isChecked)
    }

    fun handleOnEdit(id: Int) {
        setEditing(id)
    }

    fun handleOnSave(id: Int, title: String) {
        props.saveTodo(id, title)
        setEditing(-1)
    }

    fun handleOnCancel() {
        setEditing(-1)
    }

    section("main") {
        input(type = InputType.checkBox, classes = "toggle-all") {
            attrs {
                id = "toggle-all"
                onClickFunction = ::handleToogleAll
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
                todoItemComponent(key = "${it.id}", id = it.id, editing = editing == it.id) {
                    attrs {
                        onEdit = ::handleOnEdit
                        onSave = ::handleOnSave
                        onCancel = ::handleOnCancel
                    }
                }
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
    var saveTodo: (id: Int, text: String) -> Unit
}

fun TodoListStateProps.mapStateToProps(state: State, ownProps: OwnTodoListPros) {
    todos = getVisibleTodos(state.todos, state.visibility)
    activeTodoCount = getVisibleTodos(state.todos, Visibility.SHOW_ACTIVE).size
}

fun TodoListDispatchProps.mapDispatchToProps(dispatch: (RAction) -> WrapperAction, ownProps: OwnTodoListPros) {
    toogleAll = { checked -> dispatch(ToggleAllTodosAction(checked)) }
    saveTodo = { id: Int, title: String -> dispatch(SaveTodoAction(id, title)) }
}

val TodoListConnector = rConnect<State, RAction, WrapperAction, OwnTodoListPros, TodoListStateProps, TodoListDispatchProps, ConnectedTodoListProps>(
    TodoListStateProps::mapStateToProps,
    TodoListDispatchProps::mapDispatchToProps
)
val ConnectedTodoList = TodoListConnector(TodoList)

fun RBuilder.todoListComponent() = ConnectedTodoList {}
