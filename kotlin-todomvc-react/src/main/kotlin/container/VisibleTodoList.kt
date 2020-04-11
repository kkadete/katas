package katas.todomvc.container

import katas.todomvc.actions.ToggleTodoAction
import katas.todomvc.components.TodoListComponent
import katas.todomvc.components.TodoListProps
import katas.todomvc.domain.Todo
import katas.todomvc.domain.VisibilityFilter
import katas.todomvc.reducers.State
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import redux.WrapperAction

private fun getVisibleTodos(todos: Array<Todo>, filter: VisibilityFilter): Array<Todo> = when (filter) {
    VisibilityFilter.SHOW_ALL -> todos
    VisibilityFilter.SHOW_ACTIVE -> todos.filter { !it.completed }.toTypedArray()
    VisibilityFilter.SHOW_COMPLETED -> todos.filter { it.completed }.toTypedArray()
}

private interface TodoListStateProps : RProps {
    var todos: Array<Todo>
}

private interface TodoListDispatchProps : RProps {
    var toggleTodo: (Int) -> Unit
}

val visibleTodoListComponent: RClass<RProps> = rConnect<State, ToggleTodoAction, WrapperAction, RProps, TodoListStateProps, TodoListDispatchProps, TodoListProps>(
        { state, _ ->
            todos = getVisibleTodos(state.todos, state.visibilityFilter)
        },
        { dispatch, _ ->
            toggleTodo = { dispatch(ToggleTodoAction(it)) }
        }
    )(TodoListComponent::class.js.unsafeCast<RClass<TodoListProps>>())
