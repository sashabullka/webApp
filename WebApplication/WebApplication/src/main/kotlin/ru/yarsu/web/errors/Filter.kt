package ru.yarsu.web.errors

import org.http4k.core.HttpHandler
import org.http4k.core.Filter
import ru.yarsu.web.templates.ContextAwareViewRender

class Filter(val renderer: ContextAwareViewRender): Filter{
    override fun invoke(next: HttpHandler): HttpHandler{
        return ErrorHandler(next, renderer)
    }
}