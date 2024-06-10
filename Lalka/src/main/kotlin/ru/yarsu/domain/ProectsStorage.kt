package ru.yarsu.domain

import java.time.LocalDateTime

class ProectsStorage(val proects: MutableList<Proect>) {


    fun getObjectByName(name: String): Proect{
        for(i in proects.indices){
            if(proects[i].getProectID() == name)
                return proects[i]
        }
        return proects[0]
    }

    fun getVotesByPageNum(page: Int, proect: Proect): MutableList<Golos> {
        val pageSize = 10
        val startIndex = (page - 1) * pageSize

        val endIndex = startIndex + pageSize

        val flattenedPeopleList = proect.getGolosa().toMutableList()

        if (startIndex >= proect.getGolosa().size) {
            return mutableListOf()
        }
        return if (endIndex > flattenedPeopleList.size) {

            flattenedPeopleList.subList(startIndex, flattenedPeopleList.size)
        } else {

            flattenedPeopleList.subList(startIndex, endIndex)
        }
    }


    fun countPage(): Int{
        if(proects[0].getGolosa().size % 10 == 0){
            return proects[0].getGolosa().size / 10
        }
        return (proects[0].getGolosa().size / 10) + 1
    }


    fun listNames(): List<String>{
        val names = mutableListOf<String>()
        for(element in proects){
            names.add(element.getProectID())
        }
        return names
    }

    fun votesById(id: String, proect: Proect): Golos{
        return VotesStorage(proect.getGolosa()).votesById(id)
    }

    fun editGolos(proect: Proect,golos: Golos, newName: String, date: LocalDateTime, nickName: String){
        VotesStorage(proect.getGolosa()).editGolos(golos,newName,date, nickName)
    }
    fun addGolos(proect: Proect, name: String,date: LocalDateTime, nickName: String){

        VotesStorage(proect.getGolosa()).addGolos(name,date, nickName)
    }

    fun editProect( proect: Proect,newName: String, nameTerritory: String, coordinates: String, descripton: String, date: LocalDateTime?) {
        val votes = proect.getGolosa()
        proects.set(proects.indexOf(proect), Proect(newName,nameTerritory,coordinates,descripton,votes,date, proect.getProectID()))

    }
    fun addProect(namePunkt: String, nameTerritory: String, coordinates: String, descripton: String, date: LocalDateTime?){
        proects.add(Proect(namePunkt,nameTerritory,coordinates,descripton, mutableListOf<Golos>(),date,((10000..99999).random()).toString()))
    }
    fun deleteProect(proect: Proect){
        proects.remove(proect)
    }
    fun deleteGolos(proect: Proect, golos: Golos){
        VotesStorage(proect.getGolosa()).deleteGolos(golos)
    }
}