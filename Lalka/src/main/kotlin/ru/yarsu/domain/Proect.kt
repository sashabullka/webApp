package ru.yarsu.domain

import java.time.LocalDateTime

class Proect(
    private val namePunkt: String,
    private val nameTerritory: String,
    private val coordinates: String,
    private val descripton: String,
    private val golosa: MutableList<Golos>,
    private val date: LocalDateTime?,
    private val proectID: String,

    ){

    fun getProectID(): String{
        return proectID
    }

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
    fun getGolosa(): MutableList<Golos>{
        return golosa
    }
    fun getDate(): LocalDateTime?{
        return date
    }
}