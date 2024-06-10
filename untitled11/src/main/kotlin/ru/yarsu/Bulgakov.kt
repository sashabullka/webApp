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
import ru.yarsu.domain.DataGeneration
import ru.yarsu.web.cashObj
import ru.yarsu.web.router
import java.io.File
import ru.yarsu.domain.Proect



fun main() {

    val objectMapper = jacksonObjectMapper()
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
    objectMapper.registerModule(JavaTimeModule())
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    val data = DataGeneration()

    if(!File("Data.json").exists()){
        data.generation()
    }


    val listProects: List<Proect> = objectMapper.readValue(File("Data.json").readText())




    val appWithStaticResources = routes(
        router(cashObj,listProects),
        static(ResourceLoader.Classpath("/ru/yarsu/public")),
    )

    val server = appWithStaticResources.asServer(Netty(9000)).start()

    println("Server started on http://localhost:" + server.port())
    println("Press enter to exit application.")
    readln()
    server.stop()

}
