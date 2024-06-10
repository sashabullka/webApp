package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import ru.yarsu.domain.Permisson
import ru.yarsu.storages.UserStorage
import ru.yarsu.web.models.NotInSystemVM
import ru.yarsu.web.models.UsersVM
import ru.yarsu.web.templates.ContextAwareViewRender

class UsersHandler(val renderer: ContextAwareViewRender, val permissionLens: RequestContextLens<Permisson?>, val storage: UserStorage): HttpHandler {
    override fun invoke(request: Request): Response {
        val listUsers = storage.getListUsers()
        val viewModel = UsersVM(listUsers)
        val permissions = permissionLens(request)
        return if (permissions != null && permissions.canListUsers) {
            Response(Status.OK).with(renderer(request) of viewModel)
        } else {
            Response(Status.OK).with(renderer(request) of NotInSystemVM("У вас недостаточно прав"))
        }
    }
}