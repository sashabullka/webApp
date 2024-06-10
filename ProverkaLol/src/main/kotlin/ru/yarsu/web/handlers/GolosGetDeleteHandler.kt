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
import ru.yarsu.web.models.GolosDeleteVM
import ru.yarsu.web.models.GolosEditVM
import ru.yarsu.web.models.GolosVM
import ru.yarsu.web.models.NotFoundVM
import ru.yarsu.web.models.ProectEditVM

class GolosGetDeleteHandler(val renderer: TemplateRenderer,val listProects: MutableList<Proect>,val getProectByName: GetProectByName,val storage: ProectsStorage,) : HttpHandler {
    override fun invoke(request: Request): Response {

        val id: String = request.path("id") ?: return Response(Status.NOT_FOUND)
        if (id.isEmpty()) {
            return Response(Status.NOT_FOUND)
        }
        val name: String = request.path("proect") ?: return Response(Status.NOT_FOUND)
        if (name.isEmpty() || name !in ProectsStorage(listProects).listNames()) {
            return Response(Status.NOT_FOUND)
        }
        val proect = getProectByName.get(name)
        val golos = storage.votesById(id,proect)

        val viewModel = GolosDeleteVM(golos,listProects)
        val htmlDocument = renderer(viewModel)
        return Response(Status.OK).body(htmlDocument)
    }
}