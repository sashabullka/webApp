package ru.yarsu.domain

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldNotBe
import java.io.File

class ProectsHandlerTest : FunSpec({
    val objectMapper = jacksonObjectMapper()
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
    objectMapper.registerModule(JavaTimeModule())
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    val listProects: List<Proect> = objectMapper.readValue(File("Data.json").readText())
    test("Method should return non-nullable value") {
            val tester = ProectsStorage(listProects)
            tester.countPage() shouldNotBe null

    }
    test("Method are not empty") {
        val tester = ProectsStorage(listProects)
        tester.listNames().isNotEmpty()

    }
})