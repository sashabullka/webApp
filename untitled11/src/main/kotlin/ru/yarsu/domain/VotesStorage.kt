package ru.yarsu.domain

class VotesStorage(votesConst: List<People>) {
    val votes: List<People> = votesConst


    fun sortedVotes(): List<People>{
        var sortedList = votes.sortedBy { it.getDate() }
        return sortedList
    }

    fun filter(min: Double?, max: Double?): List<People> {
        val startlist = sortedVotes()
        val filteredList = mutableListOf<People>()

        for (person in startlist) {
            val id = person.getId().toInt()
            if ((min == null || id >= min) && (max == null || id <= max)) {
                filteredList.add(person)
            }
        }

        return filteredList
    }

    fun ifEmptyFilter(min: Double?, max: Double?): Boolean{
        return filter(min,max).isEmpty()
    }
}