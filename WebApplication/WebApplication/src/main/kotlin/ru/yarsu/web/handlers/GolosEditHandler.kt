package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.findSingle
import org.http4k.core.with
import org.http4k.routing.path
import ru.yarsu.operations.GetProectByName
import ru.yarsu.errors.GolosFormErrors
import ru.yarsu.domain.Proect
import ru.yarsu.storages.ProectsStorage
import ru.yarsu.web.models.ErrorGolosFormVM
import ru.yarsu.web.templates.ContextAwareViewRender
import java.time.LocalDateTime


class GolosEditHandler(val renderer: ContextAwareViewRender, val storage: ProectsStorage, val getProectByName: GetProectByName, val listProects: MutableList<Proect>) : HttpHandler {

    override fun invoke(request: Request): Response {
        val page: Int = request.path("page")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (page < 1 || page > storage.countPage() || page.toString().isEmpty()) {
            return Response(Status.NOT_FOUND)
        }
        val id: Int = request.path("id")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (id.toString().isEmpty() || id<10000 || id>99999) {
            return Response(Status.NOT_FOUND)
        }
        val name: Int = request.path("proect")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (name.toString().isEmpty() || name.toString() !in storage.listNames()) {
            return Response(Status.NOT_FOUND)
        }

        val form = request.form()
        val newName = form.findSingle("newName")
        val formErrors = GolosFormErrors(newName)
        val errors = formErrors.getFormErrors()

        if(errors.isError()){
            return Response(Status.OK).with(renderer(request) of ErrorGolosFormVM(errors.getAllErrors(), newName))
        }
        val proecto = getProectByName.get(name)
        val golos = storage.votesById(id,proecto)
        val currentDateTime = LocalDateTime.now()



        storage.editGolos(proecto,golos,name,currentDateTime, formErrors.nullToEmpty(newName))






        return Response(Status.FOUND).header("Location",  "/$name/$page/golos/$id")
    }
}