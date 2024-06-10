package ru.yarsu.web

import org.http4k.template.PebbleTemplates
import org.http4k.template.TemplateRenderer
import ru.yarsu.web.router2



fun dfon(bool: Boolean): TemplateRenderer {
    if(bool) return PebbleTemplates().CachingClasspath()
    return PebbleTemplates().HotReload("src/main/resources")
}

val doign = dfon(true)

