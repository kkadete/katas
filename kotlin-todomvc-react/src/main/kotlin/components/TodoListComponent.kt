package katas.todomvc.components

import katas.todomvc.domain.Todo
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.ul

interface TodoListProps : RProps {
    var todos: Array<Todo>
    var toggleTodo: (Int) -> Unit
}

class TodoListComponent(props: TodoListProps) : RComponent<TodoListProps, RState>(props) {
    override fun RBuilder.render() {
        div("todo-list") {
            ul {
                props.todos.forEach {
                    todoItemComponent(it) {
                        props.toggleTodo(it.id)
                    }
                }
            }
        }
    }
}
