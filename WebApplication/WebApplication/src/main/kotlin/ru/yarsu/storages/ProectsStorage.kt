package ru.yarsu.storages

import ru.yarsu.domain.Golos
import ru.yarsu.domain.People
import ru.yarsu.domain.Proect
import java.time.LocalDateTime

class ProectsStorage(val proects: MutableList<Proect>) {


    fun getObjectByName(name: Int): Proect {
        for(i in proects.indices){
            if(proects[i].proectID == name)
                return proects[i]
        }
        return proects[0]
    }

    fun getListGolosByUser(userName: String): List<Golos>{
        val list = mutableListOf<Golos>()
        for(element in proects){
            for(i in element.golosa){
                if(i.people.userName == userName){
                    list.add(i)
                }
            }
        }
        return list
    }

    fun getVotesByPageNum(page: Int, proect: Proect, minYear: Int?, maxYear: Int?): MutableList<Golos> {
        val pageSize = 10
        val startIndex = (page - 1) * pageSize

        val endIndex = startIndex + pageSize

        val flattenedPeopleList = filter(minYear, maxYear, proect).toMutableList()

        if (startIndex >= flattenedPeopleList.size) {
            return mutableListOf()
        }
        return if (endIndex > flattenedPeopleList.size) {
            flattenedPeopleList.subList(startIndex, flattenedPeopleList.size)
        } else {
            flattenedPeopleList.subList(startIndex, endIndex)
        }
    }


fun sortedVotes(proect: Proect): List<Golos> {
    return proect.golosa.sortedBy { it.date }
}

    fun filter(minYear: Int?, maxYear: Int?, proect: Proect): List<Golos> {
        val startList = sortedVotes(proect)

        val minDate = minYear?.let { LocalDateTime.of(it, 1, 1, 0, 0) }
        val maxDate = maxYear?.let { LocalDateTime.of(it, 12, 31, 23, 59) }

        return startList.filter { person ->
            val registrationDate = person.date
            when {
                minDate != null && maxDate != null -> registrationDate != null && registrationDate >= minDate && registrationDate <= maxDate
                minDate != null -> registrationDate != null && registrationDate >= minDate
                maxDate != null -> registrationDate != null && registrationDate <= maxDate
                else -> true
            }
        }
    }



    fun ifEmptyFilter(min: Int?, max: Int?, proect: Proect, page: Int): Boolean{
        return getVotesByPageNum(page, proect, min, max).isEmpty()
    }

    fun countPagePaginator(minYear: Int?, maxYear: Int?, proect: Proect): Int{
        if(filter(minYear,maxYear,proect).size < 10){
            return 1
        }
        if(filter(minYear,maxYear,proect).size % 10 == 0){
            return filter(minYear,maxYear,proect).size / 10
        }
        return (filter(minYear,maxYear,proect).size / 10) + 1
    }
    fun countPage(): Int{
        if(proects[0].golosa.size % 10 == 0){
            return proects[0].golosa.size / 10
        }
        return (proects[0].golosa.size / 10) + 1
    }


    fun listNames(): List<String>{
        val names = mutableListOf<String>()
        for(element in proects){
            names.add(element.proectID.toString())
        }
        return names
    }

    fun votesById(id: Int, proect: Proect): Golos {
        for(person in proect.golosa){
            if(id == person.people.id){
                return person
            }

        }
        return proect.golosa[0]
    }

    fun editGolos(proect: Proect, golos: Golos, newName: Int, date: LocalDateTime, nickName: String){
        proect.golosa.set(proect.golosa.indexOf(golos), Golos(People(golos.people.id,newName.toString(), golos.people.userName),date,nickName))
    }
    fun addGolos(proect: Proect, namePunkt: String, date: LocalDateTime, nickName: String, userName: String){

        proect.golosa.add(Golos(People(((10000..99999).random()),namePunkt, userName),date,nickName))
    }

    fun editProect(proect: Proect, newName: String, nameTerritory: String, coordinates: String, descripton: String, date: LocalDateTime?,) {
        val votes = proect.golosa
        proects.set(proects.indexOf(proect), Proect(newName,nameTerritory,coordinates,descripton,votes,date, proect.proectID, proect.username))

    }
    fun addProect(namePunkt: String, nameTerritory: String, coordinates: String, descripton: String, date: LocalDateTime?, userName: String){
        proects.add(Proect(namePunkt,nameTerritory,coordinates,descripton, mutableListOf<Golos>(),date,((10000..99999).random()),userName))
    }
    fun deleteProect(proect: Proect){
        proects.remove(proect)
    }
    fun deleteGolos(proect: Proect, golos: Golos){
        proect.golosa.remove(golos)
    }
}