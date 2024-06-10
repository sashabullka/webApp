package ru.yarsu.domain

class Triangle (
    val sideA: Double,
    val sideB: Double,
    val sideC: Double,
){

    fun perimeter() = sideA + sideB + sideC

    fun enlarge(coefficient: Double) = Triangle(
        coefficient * sideA,
        coefficient * sideB,
        coefficient * sideC,
    )
}
