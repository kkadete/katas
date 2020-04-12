package katas.todomvc.components

import katas.todomvc.domain.Todo
import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.textDecoration
import kotlinx.html.js.onClickFunction
import react.RBuilder
import styled.css
import styled.styledLi

fun RBuilder.todoComponent(todo: Todo, onClick: () -> Unit) =
    styledLi {
        attrs.onClickFunction = { onClick() }
        css {
            if (todo.completed) textDecoration(TextDecorationLine.lineThrough)
        }
        +todo.text
    }
