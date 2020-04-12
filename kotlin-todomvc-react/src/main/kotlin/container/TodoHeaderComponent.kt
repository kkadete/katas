package katas.todomvc.container

import katas.todomvc.actions.AddTodoAction
import katas.todomvc.store
import kotlinx.html.InputType
import kotlinx.html.classes
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.form
import react.dom.h1
import react.dom.header
import react.dom.input
import react.redux.rConnect
import redux.WrapperAction

class TodoHeaderComponent(props: RProps) : RComponent<RProps, RState>(props) {
    private val inputRef = createRef<HTMLInputElement>()

    private val inputHandler: (Event) -> Unit = { event ->
        event.preventDefault()
        inputRef.current!!.let {
            if (it.value.trim().isNotEmpty()) {
                store.dispatch(AddTodoAction(it.value))
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
                    onSubmitFunction = inputHandler
                }
                input(type = InputType.text) {
                    attrs {
                        classes = setOf("new-todo")
                        placeholder="What needs to be done?"
                        autoFocus=true
                    }
                    ref = inputRef
                }
            }
        }
    }
}

val todoHeaderComponent: RClass<RProps> = rConnect<TodoHeaderComponent, WrapperAction>()(TodoHeaderComponent::class.js.unsafeCast<RClass<RProps>>())
