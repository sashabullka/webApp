package ru.yarsu.web

import org.http4k.core.Method
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.TemplateRenderer
import ru.yarsu.domain.Triangle
import ru.yarsu.web.handlers.HomeHandler
import ru.yarsu.web.handlers.PebbleHandler
import ru.yarsu.web.handlers.PingHandler
import ru.yarsu.web.handlers.TriangleHandler

fun router2(pebble: TemplateRenderer, listTriangles: List<Triangle>): RoutingHttpHandler{
    return routes(
        "/ping" bind Method.GET to PingHandler(),
        "/" bind Method.GET to HomeHandler(pebble),
        "/templates/pebble" bind Method.GET to PebbleHandler(pebble),
        "/triangles" bind Method.GET to TriangleHandler(pebble, listTriangles),
    )
}

/*val router = routes(
    "/ping" bind Method.GET to PingHandler(),
    "/" bind Method.GET to HomeHandler(),
    "/templates/pebble" bind Method.GET to PebbleHandler(),
)*/
