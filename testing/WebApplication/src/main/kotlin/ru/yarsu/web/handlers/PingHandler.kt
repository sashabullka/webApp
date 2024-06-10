package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

class PingHandler: HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.OK).body("pong")
    }
}
