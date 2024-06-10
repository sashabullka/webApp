package ru.yarsu.domain

interface GetProectOperation {
    fun get(name: String): Proect
}

class GetProectByName(
    private val storage: ProectsStorage
) : GetProectOperation {
    override fun get(name: String) = storage.getObjectByName(name)
}