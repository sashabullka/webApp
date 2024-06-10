package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.template.PebbleTemplates
import org.http4k.template.TemplateRenderer
import ru.yarsu.web.models.HomePageVM

class HomeHandler(_renderer: TemplateRenderer): HttpHandler {
    val renderer: TemplateRenderer = _renderer
    override fun invoke(request: Request): Response {
        val viewModel = HomePageVM("Johdwoih")
        val htmlDocument = renderer(viewModel)
        return Response(Status.OK).body(htmlDocument)
    }
}