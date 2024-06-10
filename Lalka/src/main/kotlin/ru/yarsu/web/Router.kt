package ru.yarsu.web

import org.http4k.core.Method
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.TemplateRenderer
import ru.yarsu.domain.GetProectByName
import ru.yarsu.web.handlers.HomeHandler
/*import ru.yarsu.web.handlers.TriangleHandler*/
import ru.yarsu.domain.Proect
import ru.yarsu.domain.ProectsStorage
import ru.yarsu.domain.VotesStorage
import ru.yarsu.web.handlers.GolosAddHandler
import ru.yarsu.web.handlers.GolosDeleteHandler
import ru.yarsu.web.handlers.GolosEditHandler
import ru.yarsu.web.handlers.GolosGetAddHandler
import ru.yarsu.web.handlers.GolosGetDeleteHandler
import ru.yarsu.web.handlers.GolosGetEditHandler
import ru.yarsu.web.handlers.GolosHandler
import ru.yarsu.web.handlers.ProectAddHandler
import ru.yarsu.web.handlers.ProectDeleteHandler
import ru.yarsu.web.handlers.ProectEditHandler
import ru.yarsu.web.handlers.ProectGetAddHandler
import ru.yarsu.web.handlers.ProectGetDeleteHandler
import ru.yarsu.web.handlers.ProectGetEditHandler
import ru.yarsu.web.handlers.ProectsListHandler
import ru.yarsu.web.models.GolosDeleteVM

fun router(cashType: TemplateRenderer,storage: ProectsStorage): RoutingHttpHandler{
    val listProects = storage.proects
    val proectsStorage = ProectsStorage(listProects)
    val getProectByNameOperation = GetProectByName(proectsStorage)
    val proectsListHandler = ProectsListHandler(cashType, proectsStorage,listProects, getProectByNameOperation)
    val proectsEditHadler = ProectEditHandler(cashType,proectsStorage,getProectByNameOperation,listProects)
    return routes(
        "/" bind Method.GET to HomeHandler(cashType, listProects),
        "/{proect}/n{page}" bind Method.GET to proectsListHandler,
        "/{proect}/{page}/edit" bind Method.POST to proectsEditHadler,
        "/{proect}/{page}/edit" bind Method.GET to ProectGetEditHandler(cashType, listProects,getProectByNameOperation),
        "/add" bind Method.POST to ProectAddHandler(cashType,proectsStorage,getProectByNameOperation,listProects),
        "/add" bind Method.GET to ProectGetAddHandler(cashType, listProects),
        "/{proect}/{page}/delete" bind Method.POST to ProectDeleteHandler(cashType,proectsStorage,getProectByNameOperation,listProects),
        "/{proect}/{page}/delete" bind Method.GET to ProectGetDeleteHandler(cashType, listProects,getProectByNameOperation),
        "/{proect}/{page}/golos/{id}" bind Method.GET to GolosHandler(cashType, listProects,getProectByNameOperation),
        "/{proect}/{page}/golos/{id}/edit" bind Method.POST to GolosEditHandler(cashType,proectsStorage,getProectByNameOperation,listProects),
        "/{proect}/{page}/golos/{id}/edit" bind Method.GET to GolosGetEditHandler(cashType,listProects,getProectByNameOperation,proectsStorage),
        "/{proect}/{page}/add" bind Method.POST to GolosAddHandler(cashType,proectsStorage,getProectByNameOperation,listProects),
        "/{proect}/{page}/add" bind Method.GET to GolosGetAddHandler(cashType,listProects),
        "/{proect}/{page}/golos/{id}/delete" bind Method.POST to GolosDeleteHandler(cashType,proectsStorage,getProectByNameOperation,listProects),
        "/{proect}/{page}/golos/{id}/delete" bind Method.GET to GolosGetDeleteHandler(cashType,listProects,getProectByNameOperation,proectsStorage),
        )
}
