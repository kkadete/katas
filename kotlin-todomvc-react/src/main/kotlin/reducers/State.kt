package katas.todomvc.reducers

import katas.todomvc.domain.Todo
import katas.todomvc.domain.Visibility

data class State(
    val todos: Array<Todo> = emptyArray(),
    val visibility: Visibility = Visibility.SHOW_ALL,
    val editing: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class.js != other::class.js) return false

        other as State

        if (!todos.contentEquals(other.todos)) return false
        if (visibility != other.visibility) return false
        if (editing != other.editing) return false

        return true
    }

    override fun hashCode(): Int {
        var result = todos.contentHashCode()
        result = 31 * result + visibility.hashCode()
        result = 31 * result + editing.hashCode()
        return result
    }
}

