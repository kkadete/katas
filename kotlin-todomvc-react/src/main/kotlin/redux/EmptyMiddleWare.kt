package katas.todomvc.redux

import redux.Middleware
import redux.MiddlewareApi
import redux.RAction

class EmptyMiddleware<S, A : RAction, R>() {
    fun createMiddleware(): Middleware<S, A, R, A, R> = ::emptyMiddleware

    private fun emptyMiddleware(api: MiddlewareApi<S, A, R>): ((A) -> R) -> (A) -> R {
        return { nextDispatch ->
            { action ->
                val result = nextDispatch(action)
                result
            }
        }
    }
}
