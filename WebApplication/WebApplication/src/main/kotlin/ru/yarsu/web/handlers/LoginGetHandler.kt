package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import ru.yarsu.web.models.LoginVM
import ru.yarsu.web.templates.ContextAwareViewRender

class LoginGetHandler(val renderer: ContextAwareViewRender, ) : HttpHandler {

    override fun invoke(request: Request): Response {

        val viewModel = LoginVM()
        return Response(Status.OK).with(renderer(request) of viewModel)
    }
}