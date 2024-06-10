package ru.yarsu.operations

import ru.yarsu.domain.Proect
import ru.yarsu.storages.ProectsStorage

interface GetProectOperation {
    fun get(name: Int): Proect
}

class GetProectByName(
    private val storage: ProectsStorage
) : GetProectOperation {
    override fun get(name: Int) = storage.getObjectByName(name)
}