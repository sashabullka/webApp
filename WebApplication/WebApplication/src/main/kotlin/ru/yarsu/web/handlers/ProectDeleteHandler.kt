package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.findSingle
import org.http4k.routing.path
import ru.yarsu.operations.GetProectByName
import ru.yarsu.domain.Proect
import ru.yarsu.storages.ProectsStorage
import ru.yarsu.web.templates.ContextAwareViewRender


class ProectDeleteHandler(val renderer: ContextAwareViewRender, val storage: ProectsStorage, val getProectByName: GetProectByName, val listProects: MutableList<Proect>,) : HttpHandler {
    override fun invoke(request: Request): Response {

        val name: Int = request.path("proect")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (name.toString().isEmpty() || name.toString() !in storage.listNames()) {
            return Response(Status.NOT_FOUND)
        }

        val form = request.form()
        val check = form.findSingle("checkboxic")
        val proecto = getProectByName.get(name)

        if(check != null){
            storage.deleteProect(proecto)
            return Response(Status.FOUND).header("Location", "/proects")
        }








        return Response(Status.FOUND).header("Location",  "/$name/1/delete")
    }
}