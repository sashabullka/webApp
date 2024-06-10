package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.findSingle
import org.http4k.core.with
import org.http4k.routing.path
import ru.yarsu.domain.User
import ru.yarsu.errors.UserFormErrors
import ru.yarsu.storages.UserStorage
import ru.yarsu.web.models.ErrorUserFormVM
import ru.yarsu.web.templates.ContextAwareViewRender
import java.time.LocalDateTime


class UserEditHandler(val renderer: ContextAwareViewRender,val storage: UserStorage) : HttpHandler {
    override fun invoke(request: Request): Response {

        val name:String = request.path("username") ?: return Response(Status.NOT_FOUND)
        if (name.isEmpty() || name !in storage.listNames()) {
            return Response(Status.NOT_FOUND)
        }

        val form = request.form()
        var nameLog = form.findSingle("login")
        var password = form.findSingle("password")
        val role = form.findSingle("role")
        if (nameLog != null) {
            nameLog = nameLog.trim()
        }
        if (password != null) {
            password = password.trim()
        }

        val currentDateTime = LocalDateTime.now()


        val formErrors = UserFormErrors(nameLog,password,password, storage)
        val errors = formErrors.getFormErrors()
        if(errors.isError()){
            return Response(Status.OK).with(renderer(request) of ErrorUserFormVM(nameLog!!, password!!, password, errors.getAllErrors()))
        }

        storage.updateUserByUsername(name,User(nameLog!!,password!!,currentDateTime,role!!))








        return Response(Status.FOUND).header("Location",  "/users")
    }
}