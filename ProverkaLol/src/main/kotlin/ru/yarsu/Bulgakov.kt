package ru.yarsu

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.http4k.routing.ResourceLoader
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Netty
import org.http4k.server.asServer
import org.http4k.template.TemplateRenderer
import ru.yarsu.domain.DataGeneration
import ru.yarsu.web.router
import java.io.File
import ru.yarsu.domain.Proect
import ru.yarsu.domain.ProectsStorage
import ru.yarsu.web.cash
import ru.yarsu.web.errors.Filter
import kotlin.concurrent.thread


fun main() {

    val objectMapper = jacksonObjectMapper()
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
    objectMapper.registerModule(JavaTimeModule())
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    val data = DataGeneration()

    if(!File("Data.json").exists()){
        data.generation()
    }


    val listProects: MutableList<Proect> = objectMapper.readValue(File("Data.json").readText())
    val list = ProectsStorage(listProects)
    val renderer = cash(true)

    val filter = Filter(renderer)
    val appWithStaticResources = routes(
        router(renderer,list),
        static(ResourceLoader.Classpath("/ru/yarsu/public")),
    ).withFilter(filter)

    val server = appWithStaticResources.asServer(Netty(9000)).start()

    println("Server started on http://localhost:" + server.port())
    println("Press enter to exit application.")
    val hook = thread(start = false) {
        val fileSpecialistsWrite = File("Data.json")
        val stringSpecialists = objectMapper.writeValueAsString(list.proects)
        fileSpecialistsWrite.writeText(stringSpecialists, Charsets.UTF_8)
    }
    Runtime.getRuntime().addShutdownHook(hook)
    readln()
    server.stop()

}
