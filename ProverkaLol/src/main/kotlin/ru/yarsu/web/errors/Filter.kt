package ru.yarsu.web.errors

import org.http4k.core.HttpHandler
import org.http4k.core.Filter
import org.http4k.template.TemplateRenderer


class Filter(val renderer: TemplateRenderer): Filter{
    override fun invoke(next: HttpHandler): HttpHandler{
        return ErrorHandler(next, renderer)
    }
}