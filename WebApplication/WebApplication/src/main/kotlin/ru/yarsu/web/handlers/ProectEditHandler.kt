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
import ru.yarsu.domain.Proect
import ru.yarsu.errors.ProectFormErrors
import ru.yarsu.storages.ProectsStorage
import ru.yarsu.web.models.ErrorProectFormVM
import ru.yarsu.web.templates.ContextAwareViewRender
import java.time.LocalDateTime


class ProectEditHandler(val renderer: ContextAwareViewRender, val storage: ProectsStorage, val getProectByName: GetProectByName, val listProects: MutableList<Proect>) : HttpHandler {

    override fun invoke(request: Request): Response {
        val page: Int = request.path("page")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (page < 1 || page > storage.countPage() || page.toString().isEmpty()) {

            return Response(Status.NOT_FOUND)

        }
        val name: Int = request.path("proect")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (name.toString().isEmpty() || name.toString() !in storage.listNames()) {
            return Response(Status.NOT_FOUND)
        }

        val form = request.form()
        val namePunkt = form.findSingle("namePunkt")
        val nameTerritory = form.findSingle("nameTerritory")
        val coordinates = form.findSingle("coordinates")
        val descripton = form.findSingle("descripton")
        val proecto = getProectByName.get(name)
        val currentDateTime = LocalDateTime.now()


        val formErrors = ProectFormErrors(namePunkt, nameTerritory, coordinates,descripton)
        val errors = formErrors.getFormErrors()
        if(errors.isError()){
            return Response(Status.OK).with(renderer(request) of ErrorProectFormVM(errors.getAllErrors(), namePunkt, nameTerritory, coordinates,descripton))
        }



        storage.editProect(proecto,formErrors.nullToEmpty(namePunkt),formErrors.nullToEmpty(nameTerritory),formErrors.nullToEmpty(coordinates),formErrors.nullToEmpty(descripton),currentDateTime)







        return Response(Status.FOUND).header("Location",  "/$name/$page")
    }
}