package ru.yarsu.web.errors

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.template.TemplateRenderer
import ru.yarsu.web.models.NotFoundVM

class ErrorHandler(val next: HttpHandler, val renderer: TemplateRenderer): HttpHandler{
    override fun invoke(request: Request): Response {
        val response = next(request)
        if(response.status != Status.OK){
            if(response.status == Status.NOT_FOUND){
                return response.body(renderer(NotFoundVM()))
            }
        }
        return response
    }
}
