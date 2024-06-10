package ru.yarsu

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.http4k.routing.ResourceLoader
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Netty
import org.http4k.server.asServer
import ru.yarsu.domain.Triangle
import ru.yarsu.web.doign
import ru.yarsu.web.router2
import java.io.File

val objectMapper = jacksonObjectMapper()
fun main() {
    val file = File("file.json")
    val readerSpecialists = file.readText()
    val listTriangles: List<Triangle> = objectMapper.readValue(readerSpecialists)
    val appWithStaticResources = routes(
        router2(doign, listTriangles),
        static(ResourceLoader.Classpath("/ru/yarsu/public")),
    )

    val server = appWithStaticResources.asServer(Netty(9000)).start()

    println("Server started on http://localhost:" + server.port())
    println("Press enter to exit application.")
    readln()
    server.stop()

}
