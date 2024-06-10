package ru.yarsu.web.errors

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import ru.yarsu.web.models.NotFoundVM
import ru.yarsu.web.templates.ContextAwareViewRender

class ErrorHandler(val next: HttpHandler, val renderer: ContextAwareViewRender): HttpHandler {
    override fun invoke(request: Request): Response {
        val response = next(request)
        if (response.status == Status.NOT_FOUND) {
            return response.with(renderer(request) of NotFoundVM())
        }
        return response
    }
}
