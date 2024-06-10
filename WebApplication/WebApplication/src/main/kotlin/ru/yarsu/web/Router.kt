package ru.yarsu.web

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.lens.BiDiLens
import org.http4k.lens.RequestContextLens
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import ru.yarsu.domain.Permisson
import ru.yarsu.domain.User
import ru.yarsu.operations.GetProectByName
import ru.yarsu.operations.PasswordCheckerOperation
import ru.yarsu.web.handlers.HomeHandler
import ru.yarsu.storages.ProectsStorage
import ru.yarsu.operations.UserRegistrationOperation
import ru.yarsu.storages.UserStorage
import ru.yarsu.web.handlers.GolosAddHandler
import ru.yarsu.web.handlers.GolosDeleteHandler
import ru.yarsu.web.handlers.GolosEditHandler
import ru.yarsu.web.handlers.GolosGetAddHandler
import ru.yarsu.web.handlers.GolosGetDeleteHandler
import ru.yarsu.web.handlers.GolosGetEditHandler
import ru.yarsu.web.handlers.GolosHandler
import ru.yarsu.web.handlers.LoginGetHandler
import ru.yarsu.web.handlers.LoginHandler
import ru.yarsu.web.handlers.LogoutHandler
import ru.yarsu.web.handlers.ProectAddHandler
import ru.yarsu.web.handlers.ProectDeleteHandler
import ru.yarsu.web.handlers.ProectEditHandler
import ru.yarsu.web.handlers.ProectGetAddHandler
import ru.yarsu.web.handlers.ProectGetDeleteHandler
import ru.yarsu.web.handlers.ProectGetEditHandler
import ru.yarsu.web.handlers.ProectsHandler
import ru.yarsu.web.handlers.ProectsListHandler
import ru.yarsu.web.handlers.UserAddHandler
import ru.yarsu.web.handlers.UserDeleteHandler
import ru.yarsu.web.handlers.UserEditHandler
import ru.yarsu.web.handlers.UserGetAddHandler
import ru.yarsu.web.handlers.UserGetDeleteHandler
import ru.yarsu.web.handlers.UserGetEditandler
import ru.yarsu.web.handlers.UserGolosaHandler
import ru.yarsu.web.handlers.UsersHandler
import ru.yarsu.web.templates.ContextAwareViewRender

fun router(cashType: ContextAwareViewRender, storage: ProectsStorage, userRegistr: UserRegistrationOperation, userPasswordChecker: PasswordCheckerOperation, userStorage: UserStorage, permissionLens: RequestContextLens<Permisson?>,userLens: BiDiLens<Request, User?>): RoutingHttpHandler{
    val listProects = storage.proects


    val getProectByNameOperation = GetProectByName(storage)
    val proectsListHandler = ProectsListHandler(cashType, storage,listProects, getProectByNameOperation)
    val proectsEditHadler = ProectEditHandler(cashType,storage,getProectByNameOperation,listProects)
    return routes(
        "/" bind Method.GET to HomeHandler(cashType, listProects),
        "/logout" bind Method.GET to LogoutHandler(),
        "/login" bind Method.POST to LoginHandler(cashType,userPasswordChecker),
        "/login" bind Method.GET to LoginGetHandler(cashType),
        "/users" bind Method.GET to UsersHandler(cashType, permissionLens, userStorage),
        "/users/{username}/delete" bind Method.POST to UserDeleteHandler(cashType, userStorage),
        "/users/{username}/delete" bind Method.GET to UserGetDeleteHandler(cashType, userStorage, ),
        "/users/{username}/edit" bind Method.POST to UserEditHandler(cashType, userStorage),
        "/users/{username}/edit" bind Method.GET to UserGetEditandler(cashType,userStorage),
        "/{proect}/{page}/watch" bind Method.GET to UserGolosaHandler(cashType,storage, userLens, getProectByNameOperation, permissionLens),
        "/users/add" bind Method.POST to UserAddHandler(cashType,userRegistr),
        "/users/add" bind Method.GET to UserGetAddHandler(cashType, permissionLens),
        "/proects" bind Method.GET to ProectsHandler(cashType, listProects),
        "/{proect}/{page}" bind Method.GET to proectsListHandler,
        "/{proect}/{page}/edit" bind Method.POST to proectsEditHadler,
        "/{proect}/{page}/edit" bind Method.GET to ProectGetEditHandler(cashType, listProects,getProectByNameOperation, storage, permissionLens),
        "/add" bind Method.POST to ProectAddHandler(cashType,storage,getProectByNameOperation,userLens),
        "/add" bind Method.GET to ProectGetAddHandler(cashType, permissionLens),
        "/{proect}/{page}/delete" bind Method.POST to ProectDeleteHandler(cashType,storage,getProectByNameOperation,listProects),
        "/{proect}/{page}/delete" bind Method.GET to ProectGetDeleteHandler(cashType, listProects,getProectByNameOperation,storage, permissionLens),
        "/{proect}/{page}/golos/{id}" bind Method.GET to GolosHandler(cashType, listProects,getProectByNameOperation,storage),
        "/{proect}/{page}/golos/{id}/edit" bind Method.POST to GolosEditHandler(cashType,storage,getProectByNameOperation,listProects),
        "/{proect}/{page}/golos/{id}/edit" bind Method.GET to  GolosGetEditHandler(cashType,listProects,getProectByNameOperation,storage, permissionLens),
        "/{proect}/{page}/add" bind Method.POST to GolosAddHandler(cashType,storage,getProectByNameOperation,listProects,userLens),
        "/{proect}/{page}/add" bind Method.GET to GolosGetAddHandler(cashType, permissionLens),
        "/{proect}/{page}/golos/{id}/delete" bind Method.POST to GolosDeleteHandler(cashType,storage,getProectByNameOperation,listProects),
        "/{proect}/{page}/golos/{id}/delete" bind Method.GET to GolosGetDeleteHandler(cashType,listProects,getProectByNameOperation,storage, permissionLens),
        )
}
