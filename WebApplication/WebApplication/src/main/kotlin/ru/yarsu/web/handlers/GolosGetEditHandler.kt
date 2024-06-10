package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import org.http4k.routing.path
import ru.yarsu.domain.Permisson
import ru.yarsu.operations.GetProectByName
import ru.yarsu.domain.Proect
import ru.yarsu.storages.ProectsStorage
import ru.yarsu.web.models.GolosEditVM
import ru.yarsu.web.models.NotInSystemVM
import ru.yarsu.web.templates.ContextAwareViewRender

class GolosGetEditHandler(val renderer: ContextAwareViewRender, val listProects: MutableList<Proect>, val getProectByName: GetProectByName, val storage: ProectsStorage,val permissionLens: RequestContextLens<Permisson?>) : HttpHandler {
    override fun invoke(request: Request): Response {

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

        val viewModel = GolosEditVM(golos)
        val permissions = permissionLens(request)
        return if (permissions != null && permissions.canEditGolos) {
            Response(Status.OK).with(renderer(request) of viewModel)
        } else {
            Response(Status.OK).with(renderer(request) of NotInSystemVM("У вас недостаточно прав"))
        }
    }
}