package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import ru.yarsu.domain.Proect
import ru.yarsu.web.models.ProectsPageVM
import ru.yarsu.web.templates.ContextAwareViewRender

class ProectsHandler(val renderer: ContextAwareViewRender, val listProects: List<Proect>): HttpHandler {
    override fun invoke(request: Request): Response {

        val viewModel = ProectsPageVM(listProects)
        return Response(Status.OK).with(renderer(request) of viewModel)
    }
}