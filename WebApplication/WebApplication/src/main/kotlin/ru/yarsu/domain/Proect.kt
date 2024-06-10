package ru.yarsu.domain

import java.time.LocalDateTime

class Proect(
    val namePunkt: String,
    val nameTerritory: String,
    val coordinates: String,
    val descripton: String,
    val golosa: MutableList<Golos>,
    val date: LocalDateTime?,
    val proectID: Int,
    val username: String,

    )