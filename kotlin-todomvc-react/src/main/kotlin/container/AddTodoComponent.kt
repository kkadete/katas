package katas.todomvc.container

import katas.todomvc.actions.AddTodoAction
import katas.todomvc.store
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.button
import react.dom.div
import react.dom.form
import react.dom.input
import react.redux.rConnect
import redux.WrapperAction

class AddTodoComponent(props: RProps) : RComponent<RProps, RState>(props) {
    private val inputRef = createRef<HTMLInputElement>()

    override fun RBuilder.render() {
        div {
            form {
                attrs.onSubmitFunction = { event ->
                    event.preventDefault()
                    inputRef.current!!.let {
                        if (it.value.trim().isNotEmpty()) {
                            store.dispatch(AddTodoAction(it.value))
                            it.value = ""
                        }
                    }
                }
                input(type = InputType.text) {
                    ref = inputRef
                }
                button(type = ButtonType.submit) {
                    +"Add Todo"
                }
            }
        }
    }
}


val addTodoComponent: RClass<RProps> = rConnect<AddTodoComponent, WrapperAction>()(AddTodoComponent::class.js.unsafeCast<RClass<RProps>>())
