package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import ru.yarsu.domain.Permisson
import ru.yarsu.web.models.NotInSystemVM
import ru.yarsu.web.models.ProectAddVM
import ru.yarsu.web.templates.ContextAwareViewRender

class ProectGetAddHandler(val renderer: ContextAwareViewRender,val permissionLens: RequestContextLens<Permisson?> ) : HttpHandler {

    override fun invoke(request: Request): Response {

        val viewModel = ProectAddVM()
        val permissions = permissionLens(request)
        return if (permissions != null && permissions.canAddProect) {
            Response(Status.OK).with(renderer(request) of viewModel)
        } else {
            Response(Status.OK).with(renderer(request) of NotInSystemVM("У вас недостаточно прав"))
        }
    }
}