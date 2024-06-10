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


class ProectDeleteHandler(val renderer: TemplateRenderer, val storage: ProectsStorage,val getProectByName: GetProectByName,val listProects: MutableList<Proect>,) : HttpHandler {
    override fun invoke(request: Request): Response {

        val name: String = request.path("proect") ?: return Response(Status.NOT_FOUND)
        if (name.isEmpty() || name !in ProectsStorage(listProects).listNames()) {
            return Response(Status.NOT_FOUND)
        }

        val form = request.form()
        val check = form.findSingle("checkboxic")
        val proecto = getProectByName.get(name)

        if(check != null){
            storage.deleteProect(proecto)
            return Response(Status.FOUND).header("Location", "/")
        }








        return Response(Status.FOUND).header("Location",  "/$name/1/delete")
    }
}