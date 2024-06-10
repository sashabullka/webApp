package ru.yarsu.domain

import java.time.LocalDateTime

class VotesStorage(private val votes: MutableList<Golos>) {


    fun sortedVotes(): List<Golos>{
        var sortedList = votes.sortedBy { it.getDate() }
        return sortedList
    }


    fun filter(minYear: Int?, maxYear: Int?): List<Golos> {
        val startList = sortedVotes()
        val filteredList = mutableListOf<Golos>()

        if (minYear != null && maxYear != null) {
            val minDate = LocalDateTime.of(minYear, 1, 1, 0, 0)
            val maxDate = LocalDateTime.of(maxYear, 12, 31, 23, 59)

            for (person in startList) {
                val registrationDate = person.getDate()
                if (registrationDate != null) {
                    if (registrationDate >= minDate && registrationDate <= maxDate) {
                        filteredList.add(person)
                    }
                }
            }
            return filteredList
        }

        return startList
    }

    fun votesById(id: String): Golos{
        for(person in votes){
            if(id == person.getPeople().getId()){
                return person
            }

        }
        return votes[0]
    }

    fun editGolos(golos: Golos, newName: String, date: LocalDateTime, nickName: String){
        votes.set(votes.indexOf(golos), Golos(People(golos.getPeople().getId(),newName),date,nickName))
    }
    fun addGolos(name: String,date: LocalDateTime, nickName: String){

        votes.add(Golos(People(((10000..99999).random()).toString(),name),date,nickName))
    }
    fun deleteGolos(golos: Golos){
        votes.remove(golos)
    }
    fun ifEmptyFilter(min: Int?, max: Int?): Boolean{
        return filter(min,max).isEmpty()
    }
}