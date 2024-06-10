package ru.yarsu.web.handlers

import org.http4k.core.*
import org.http4k.template.PebbleTemplates
import org.http4k.template.TemplateRenderer
import ru.yarsu.web.models.PebbleVM

class PebbleHandler(_renderer: TemplateRenderer) : HttpHandler {
    val renderer: TemplateRenderer = _renderer
    override fun invoke(request: Request): Response {
        val viewModel = PebbleVM("Hello there!")
        val htmlDocument = renderer(viewModel)
        return Response(Status.OK).body(htmlDocument)
    }
}
