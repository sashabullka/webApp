package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import ru.yarsu.domain.Permisson
import ru.yarsu.web.models.AddUserVM
import ru.yarsu.web.templates.ContextAwareViewRender

class UserGetAddHandler(val renderer: ContextAwareViewRender, val permissionLens: RequestContextLens<Permisson?>) : HttpHandler {

    override fun invoke(request: Request): Response {
        val roles = mutableListOf<String>()
        roles.add("admin")
        roles.add("cityAdmin")
        roles.add("citizen")
        val viewModel = AddUserVM(roles)
        return Response(Status.OK).with(renderer(request) of viewModel)
    }
}