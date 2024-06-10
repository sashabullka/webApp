package ru.yarsu.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe

class TriangleTest : FunSpec({
    test("Should create bound function") {
        val triangle = Triangle(3.0, 4.0, 5.0)
        val enlargeFunction: (Double) -> Triangle = triangle::enlarge
        val newTriangle = enlargeFunction(2.0)
        newTriangle.perimeter().shouldBe(24.0.plusOrMinus(0.01))
    }
})