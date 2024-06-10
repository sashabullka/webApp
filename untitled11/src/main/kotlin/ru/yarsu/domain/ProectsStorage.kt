package ru.yarsu.domain

class ProectsStorage(proectsConst: List<Proect>) {
    val proects: List<Proect> = proectsConst

    fun getObjectByName(name: String): Proect{
        for(i in proects.indices){
            if(proects[i].getNamePunkt() == name)
                return proects[i]
        }
        return proects[0]
    }

    fun getVotesByPageNum(page: Int, proect: Proect): List<People> {
        val pageSize = 10
        val startIndex = (page - 1) * pageSize
        val endIndex = startIndex + pageSize
        val flattenedPeopleList = proect.getPeople().toList()
        if (startIndex >= proects[0].getPeople().size) {
            return emptyList()
        }
        return if (endIndex > flattenedPeopleList.size) {

            flattenedPeopleList.subList(startIndex, flattenedPeopleList.size)
        } else {

            flattenedPeopleList.subList(startIndex, endIndex)
        }
    }


    fun countPage(): Int{
        if(proects[0].getPeople().size % 11 == 0){
            return proects[0].getPeople().size / 11
        }
        return (proects[0].getPeople().size / 11) + 1
    }


    fun listNames(): List<String>{
        val names = mutableListOf<String>()
        for(element in proects){
            names.add(element.getNamePunkt())
        }
        return names
    }
}