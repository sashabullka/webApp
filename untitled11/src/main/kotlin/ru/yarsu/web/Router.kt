package ru.yarsu.web

import org.http4k.core.Method
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.TemplateRenderer
import ru.yarsu.domain.GetProectByName
import ru.yarsu.domain.GetVotesByPage
import ru.yarsu.web.handlers.HomeHandler
/*import ru.yarsu.web.handlers.TriangleHandler*/
import ru.yarsu.domain.Proect
import ru.yarsu.domain.ProectsStorage
import ru.yarsu.web.handlers.ProectsListHandler

fun router(cashType: TemplateRenderer,listProects: List<Proect>): RoutingHttpHandler{

    val proectsStorage = ProectsStorage(listProects)
    val getProectByNameOperation = GetProectByName(proectsStorage)
    val getVotesByPageOperation = GetVotesByPage(proectsStorage)
    val proectsListHandler = ProectsListHandler(cashType, listProects, getProectByNameOperation, getVotesByPageOperation)
    return routes(
        "/" bind Method.GET to HomeHandler(cashType, listProects),
        "/proects/{proect}/num{page}" bind Method.GET to proectsListHandler,
    )
}
