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
import ru.yarsu.domain.ProectFormErrors
//import ru.yarsu.domain.FormErrors
import ru.yarsu.domain.ProectsStorage
import ru.yarsu.web.models.ErrorProectFormVM
import ru.yarsu.web.models.NotFoundVM
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ProectEditHandler(val renderer: TemplateRenderer, val storage: ProectsStorage,val getProectByName: GetProectByName,val listProects: MutableList<Proect>,) : HttpHandler {

    override fun invoke(request: Request): Response {
        val page: Int = request.path("page")?.toIntOrNull() ?: return Response(Status.NOT_FOUND)
        if (page < 1 || page > ProectsStorage(listProects).countPage() || page.toString().isEmpty()) {

            return Response(Status.NOT_FOUND)

        }
        val name: String = request.path("proect") ?: return Response(Status.NOT_FOUND)
        if (name.isEmpty() || name !in ProectsStorage(listProects).listNames()) {

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
            return Response(Status.OK).body(renderer(ErrorProectFormVM(errors.getAllErrors(), namePunkt, nameTerritory, coordinates,descripton,listProects)))
        }



        storage.editProect(proecto,formErrors.nullToEmpty(namePunkt),formErrors.nullToEmpty(nameTerritory),formErrors.nullToEmpty(coordinates),formErrors.nullToEmpty(descripton),currentDateTime)







        return Response(Status.FOUND).header("Location",  "/$name/n$page")
    }
}