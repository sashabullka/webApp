package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.routing.path
import ru.yarsu.storages.UserStorage
import ru.yarsu.web.models.EditUserVM
import ru.yarsu.web.templates.ContextAwareViewRender

class UserGetEditandler(val renderer: ContextAwareViewRender,val storage: UserStorage) : HttpHandler {

    override fun invoke(request: Request): Response {
        val name:String = request.path("username") ?: return Response(Status.NOT_FOUND)
        if (name.isEmpty() || name !in storage.listNames()) {
            return Response(Status.NOT_FOUND)
        }
        val roles = mutableListOf<String>()
        roles.add("admin")
        roles.add("cityAdmin")
        roles.add("citizen")
        val viewModel = EditUserVM(roles)
        return Response(Status.OK).with(renderer(request) of viewModel)
    }
}