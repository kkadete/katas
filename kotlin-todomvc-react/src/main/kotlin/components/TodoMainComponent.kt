package katas.todomvc.components

import katas.todomvc.actions.DestroyTodoAction
import katas.todomvc.actions.SaveTodoAction
import katas.todomvc.actions.ToggleAllTodosAction
import katas.todomvc.actions.ToggleTodoAction
import katas.todomvc.domain.Todo
import katas.todomvc.domain.Visibility
import katas.todomvc.reducers.State
import katas.todomvc.selectors.getVisibleTodos
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.RBuilder
import react.RProps
import react.dom.input
import react.dom.label
import react.dom.section
import react.dom.ul
import react.invoke
import react.key
import react.rFunction
import react.redux.rConnect
import react.useState
import redux.RAction
import redux.WrapperAction

val TodoMain = rFunction("TodoMainComponent") { props: ConnectedTodoMainProps ->
    val (editing, setEditing) = useState(-1)

    val handleToogleAll: (event: Event) -> Unit = { event ->
        val target = (event.currentTarget as HTMLInputElement)
        val isChecked = target.checked

        props.toogleAll(isChecked)
    }

    val handleOnToogle: (todoId: Int) -> Unit = { todoId ->
        props.toggleTodo(todoId)
    }

    val handleOnEdit: (id: Int) -> Unit = { id ->
        setEditing(id)
    }

    val handleOnSave: (id: Int, title: String) -> Unit = { id, title ->
        props.saveTodo(id, title)

        setEditing(-1)
    }

    val handleOnCancel: () -> Unit = {
        setEditing(-1)
    }

    val handleOnDestroy: (id: Int) -> Unit = { id ->
       props.destroyTodo(id)
    }

    section("main") {
        input(type = InputType.checkBox, classes = "toggle-all") {
            attrs {
                id = "toggle-all"
                onChangeFunction = handleToogleAll
                checked = props.activeTodoCount == 0
            }
        }
        label {
            attrs {
                htmlFor = "toggle-all"
            }
            +"Mark all as complete"
        }
        ul("todo-list") {
            props.todos.forEach {
                todoItemComponent {
                    attrs {
                        key = "${it.id}"
                        id = it.id
                        isEditing = editing == it.id
                        onToogle = handleOnToogle
                        onDestroy = handleOnDestroy
                        onEdit = handleOnEdit
                        onSave = handleOnSave
                        onCancel = handleOnCancel
                    }
                }
            }
        }
    }
}

interface ConnectedTodoMainProps : OwnTodoMainPros, TodoMainStateProps, TodoMainDispatchProps

interface OwnTodoMainPros : RProps

interface TodoMainStateProps : RProps {
    var todos: Array<Todo>
    var activeTodoCount: Int
}

interface TodoMainDispatchProps : RProps {
    var toogleAll: (checked: Boolean) -> Unit
    var saveTodo: (id: Int, text: String) -> Unit
    var toggleTodo: (Int) -> Unit
    var destroyTodo: (Int) -> Unit
}

fun TodoMainStateProps.mapStateToProps(state: State, ownProps: OwnTodoMainPros) {
    todos = getVisibleTodos(state.todos, state.visibility)
    activeTodoCount = getVisibleTodos(state.todos, Visibility.SHOW_ACTIVE).size
}

fun TodoMainDispatchProps.mapDispatchToProps(dispatch: (RAction) -> WrapperAction, ownProps: OwnTodoMainPros) {
    toogleAll = { checked -> dispatch(ToggleAllTodosAction(checked)) }
    saveTodo = { id: Int, title: String -> dispatch(SaveTodoAction(id, title)) }
    toggleTodo = { dispatch(ToggleTodoAction(it)) }
    destroyTodo = { dispatch(DestroyTodoAction(it)) }
}

val TodoMainConnector = rConnect<State, RAction, WrapperAction, OwnTodoMainPros, TodoMainStateProps, TodoMainDispatchProps, ConnectedTodoMainProps>(
    TodoMainStateProps::mapStateToProps,
    TodoMainDispatchProps::mapDispatchToProps
)
val ConnectedTodoMain = TodoMainConnector(TodoMain)

fun RBuilder.todoMainComponent() = ConnectedTodoMain {}
