package ru.yarsu.storages

import ru.yarsu.domain.Permisson

class PermissonStorage {
    val admin = Permisson(true,true,true,true,true,true,true,true,)
    val adminOfCity = Permisson(true,true,true,true,true,true,false,true,)
    val unauthorized = Permisson(false,false,false,false,false,false,false,false,)
    val citizen = Permisson(false,true,false,true,false,true,false,true,)


}