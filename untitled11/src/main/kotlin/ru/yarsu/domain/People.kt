package ru.yarsu.domain

import java.time.LocalDateTime

class People(
    private val id: String,
    private val date: LocalDateTime?,
){
    fun getId(): String{
        return id
    }
    fun getDate(): LocalDateTime?{
        return date
    }

}

