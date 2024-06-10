package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.findSingle
import org.http4k.routing.path
import ru.yarsu.storages.UserStorage
import ru.yarsu.web.templates.ContextAwareViewRender


class UserDeleteHandler(val renderer: ContextAwareViewRender, val storage: UserStorage, ) : HttpHandler {
    override fun invoke(request: Request): Response {

        val name:String = request.path("username") ?: return Response(Status.NOT_FOUND)
        if (name.isEmpty() || name !in storage.listNames()) {
            return Response(Status.NOT_FOUND)
        }

        val form = request.form()
        val check = form.findSingle("checkboxic2")

        if(check != null){
            storage.deleteUserByNameLog(name)
            return Response(Status.FOUND).header("Location", "/users")
        }








        return Response(Status.FOUND).header("Location",  "/users/$name/delete")
    }
}