package ru.yarsu.domain


class People(
    private val id: String,
    private val namePunkt: String,

){
    fun getId(): String{
        return id
    }

    fun getNamePunkt(): String{
        return namePunkt
    }


}

