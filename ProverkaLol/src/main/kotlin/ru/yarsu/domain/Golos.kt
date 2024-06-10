package ru.yarsu.domain

import java.time.LocalDateTime

class Golos(
    private val people: People,
    private val date: LocalDateTime?,
    private val nick: String,
){
    fun getPeople(): People{
        return people
    }
    fun getDate(): LocalDateTime?{
        return date
    }
    fun getNick(): String{
        return nick
    }
}

