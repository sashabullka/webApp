package ru.yarsu.web.handlers

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.kotest.haveStatus
import ru.yarsu.web.doign

class HomeHandlerTest : FunSpec({
    test("Should respond with non-empty body and good status") {
        val request = Request(Method.GET, "/")
        val handler = HomeHandler(doign)
        val response = handler(request)
        response should haveStatus(Status.OK)
        response.body.length shouldNotBe null
        response.body.length?.shouldBeGreaterThan(1L)
    }
})