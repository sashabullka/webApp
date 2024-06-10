package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.findSingle
import org.http4k.routing.path
import org.http4k.template.TemplateRenderer
import ru.yarsu.domain.GetProectByName
import ru.yarsu.domain.Proect
//import ru.yarsu.domain.FormErrors
import ru.yarsu.domain.ProectsStorage
import ru.yarsu.web.models.NotFoundVM
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class GolosDeleteHandler(val renderer: TemplateRenderer,val storage: ProectsStorage,val getProectByName: GetProectByName,val listProects: MutableList<Proect>,) : HttpHandler {
    override fun invoke(request: Request): Response {

        val name: String = request.path("proect") ?: return Response(Status.NOT_FOUND)
        if (name.isEmpty() || name !in ProectsStorage(listProects).listNames()) {
            return Response(Status.NOT_FOUND)
        }
        val id: Int = request.path("id")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (id.toString().isEmpty() || id<10000 || id>99999) {
            return Response(Status.NOT_FOUND)
        }
        val page: Int = request.path("page")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (page < 1 || page > ProectsStorage(listProects).countPage() || page.toString().isEmpty()) {
            return Response(Status.NOT_FOUND)
        }

        val form = request.form()
        val check = form.findSingle("checkboxic1")
        val proecto = getProectByName.get(name)
        val golos = storage.votesById(id.toString(),proecto)
        if(check != null){
            storage.deleteGolos(proecto,golos)
            return Response(Status.FOUND).header("Location", "/$name/n1")
        }








        return Response(Status.FOUND).header("Location",  "/$name/$page/golos/$id/delete")
    }
}