package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import org.http4k.template.TemplateRenderer
import ru.yarsu.domain.GetProectByName
import ru.yarsu.domain.Proect
import ru.yarsu.domain.ProectsStorage
import ru.yarsu.domain.VotesStorage
import ru.yarsu.web.models.GolosVM
import ru.yarsu.web.models.NotFoundVM
import ru.yarsu.web.models.ProectAddVM
import ru.yarsu.web.models.ProectEditVM

class ProectGetAddHandler(val renderer: TemplateRenderer, val listProects: MutableList<Proect>) : HttpHandler {

    override fun invoke(request: Request): Response {

        val viewModel = ProectAddVM(listProects)
        val htmlDocument = renderer(viewModel)
        return Response(Status.OK).body(htmlDocument)
    }
}