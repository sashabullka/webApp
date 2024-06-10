package ru.yarsu.web.handlers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.kotest.haveStatus
import ru.yarsu.domain.Proect
import ru.yarsu.web.cashObj
import java.io.File

class HomeHandlerTest : FunSpec({
    val objectMapper = jacksonObjectMapper()
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
    objectMapper.registerModule(JavaTimeModule())
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    val listProects: List<Proect> = objectMapper.readValue(File("Data.json").readText())
    test("Should respond with non-empty body and good status") {
        val request = Request(Method.GET, "/")
        val handler = HomeHandler(cashObj,listProects )
        val response = handler(request)
        response should haveStatus(Status.OK)
        response.body.length shouldNotBe null
        response.body.length?.shouldBeGreaterThan(1L)
    }
})