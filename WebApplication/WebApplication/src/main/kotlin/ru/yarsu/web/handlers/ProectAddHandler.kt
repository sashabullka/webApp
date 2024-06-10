package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.findSingle
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import ru.yarsu.domain.User
import ru.yarsu.operations.GetProectByName
import ru.yarsu.errors.ProectFormErrors
import ru.yarsu.storages.ProectsStorage
import ru.yarsu.web.models.ErrorProectFormVM
import ru.yarsu.web.templates.ContextAwareViewRender
import java.time.LocalDateTime

class ProectAddHandler(
    val renderer: ContextAwareViewRender,
    val storage: ProectsStorage,
    val getProectByName: GetProectByName,
    val userLens: RequestContextLens<User?>
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val form = request.form()
        val namePunkt = form.findSingle("namePunkt")
        val nameTerritory = form.findSingle("nameTerritory")
        val coordinates = form.findSingle("coordinates")
        val descripton = form.findSingle("descripton")

        val formErrors = ProectFormErrors(namePunkt, nameTerritory, coordinates, descripton)
        val errors = formErrors.getFormErrors()
        val currentDateTime = LocalDateTime.now()
        val user: User? = userLens(request)

        if (errors.isError()) {
            return Response(Status.OK).with(renderer(request) of ErrorProectFormVM(
                errors.getAllErrors(), namePunkt, nameTerritory, coordinates, descripton))
        }

        if (user != null) {
            storage.addProect(
                formErrors.nullToEmpty(namePunkt),
                formErrors.nullToEmpty(nameTerritory),
                formErrors.nullToEmpty(coordinates),
                formErrors.nullToEmpty(descripton),
                currentDateTime,
                user.nameLog
            )
        }

        return Response(Status.FOUND).header("Location", "/proects")
    }
}
