package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import org.http4k.routing.path
import ru.yarsu.domain.Permisson
import ru.yarsu.domain.User
import ru.yarsu.operations.GetProectByName
import ru.yarsu.storages.ProectsStorage
import ru.yarsu.web.models.NotInSystemVM
import ru.yarsu.web.models.UserGolosaVM
import ru.yarsu.web.templates.ContextAwareViewRender

class UserGolosaHandler(val renderer: ContextAwareViewRender, val storage: ProectsStorage, val userLens: RequestContextLens<User?>, val getProectByName: GetProectByName, val permissionLens: RequestContextLens<Permisson?>): HttpHandler {
    override fun invoke(request: Request): Response {
        val name: Int = request.path("proect")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (name.toString().isEmpty() || name.toString() !in storage.listNames()) {
            return Response(Status.NOT_FOUND)
        }
        val proect = getProectByName.get(name)

        val page: Int = request.path("page")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (page < 1 || page > storage.countPage() || page.toString().isEmpty()) {
            return Response(Status.NOT_FOUND)
        }
        val user: User? = userLens(request)
        val listGolosa = user?.let { storage.getListGolosByUser(it.nameLog) }
        val viewModel = UserGolosaVM(listGolosa, proect, page)
        val permissions = permissionLens(request)
        return if (permissions != null && permissions.canWatchWhatHeGolosFor) {
            Response(Status.OK).with(renderer(request) of viewModel)
        } else {
            Response(Status.OK).with(renderer(request) of NotInSystemVM("У вас недостаточно прав"))
        }
    }
}