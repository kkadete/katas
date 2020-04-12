package katas.todomvc.components

import katas.todomvc.domain.Todo
import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.textDecoration
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.dom.*
import styled.css
import styled.styledLi

fun RBuilder.todoItemComponent(todo: Todo, onClick: () -> Unit) =
    styledLi {
        attrs.onClickFunction = { onClick() }
        css {
            if (todo.completed) textDecoration(TextDecorationLine.lineThrough)
        }
        div {
            form {
                input (type = InputType.checkBox, classes = "toggle"){
                }
                label {
                    +todo.text
                }
                button(classes = "destroy") {
                }
            }
        }
    }
