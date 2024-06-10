package ru.yarsu.web.handlers

import org.http4k.core.*
import org.http4k.template.PebbleTemplates
import org.http4k.template.TemplateRenderer
import ru.yarsu.domain.Triangle
import ru.yarsu.web.models.TrangleListVM

class TriangleHandler(_renderer: TemplateRenderer, _listTriangles: List<Triangle>) : HttpHandler {
    val renderer: TemplateRenderer = _renderer
    val listTriangles: List<Triangle> = _listTriangles
    override fun invoke(request: Request): Response {
        val viewModel = TrangleListVM(listTriangles)
        val htmlDocument = renderer(viewModel)
        return Response(Status.OK).body(htmlDocument)
    }
}