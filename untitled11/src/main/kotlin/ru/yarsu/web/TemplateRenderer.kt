package ru.yarsu.web

import org.http4k.template.PebbleTemplates
import org.http4k.template.TemplateRenderer
import ru.yarsu.web.router



fun cash(bool: Boolean): TemplateRenderer {
    if(bool) return PebbleTemplates().CachingClasspath()
    return PebbleTemplates().HotReload("src/main/resources")
}

val cashObj = cash(true)

