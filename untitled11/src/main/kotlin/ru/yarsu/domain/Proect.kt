package ru.yarsu.domain

import ru.yarsu.domain.People
import java.time.LocalDateTime

class Proect(
    private val namePunkt: String,
    private val nameTerritory: String,
    private val coordinates: String,
    private val descripton: String,
    private val people: Array<People>,
    private val date: LocalDateTime?,

    ){
    fun getNamePunkt(): String{
        return namePunkt
    }
    fun getNameTerritory(): String{
        return nameTerritory
    }
    fun getCoordinates(): String{
        return coordinates
    }
    fun getDescripton(): String{
        return descripton
    }
    fun getPeople(): Array<People>{
        return people
    }
    fun getDate(): LocalDateTime?{
        return date
    }
}