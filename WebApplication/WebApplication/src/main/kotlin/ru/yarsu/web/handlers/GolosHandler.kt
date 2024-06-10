package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.routing.path
import ru.yarsu.operations.GetProectByName
import ru.yarsu.domain.Proect
import ru.yarsu.storages.ProectsStorage
import ru.yarsu.web.models.GolosVM
import ru.yarsu.web.templates.ContextAwareViewRender

class GolosHandler(val renderer: ContextAwareViewRender, val listProects: MutableList<Proect>, val getProectByName: GetProectByName, val storage: ProectsStorage) : HttpHandler {
    override fun invoke(request: Request): Response {

        val page: Int = request.path("page")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (page < 1 || page > storage.countPage() || page.toString().isEmpty()) {
            return Response(Status.NOT_FOUND)
        }
        val id: Int = request.path("id")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (id.toString().isEmpty() || id<10000 || id>99999) {
            return Response(Status.NOT_FOUND)
        }
        val name: Int = request.path("proect")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (name.toString().isEmpty() || name.toString() !in storage.listNames()) {
            return Response(Status.NOT_FOUND)
        }
        val proect = getProectByName.get(name)

        val golos = storage.votesById(id,proect)
        val viewModel = GolosVM(golos, page, proect)
        return Response(Status.OK).with(renderer(request) of viewModel)
    }
}