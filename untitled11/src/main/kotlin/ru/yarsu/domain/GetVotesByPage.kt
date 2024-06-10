package ru.yarsu.domain

interface GetVotesOperation {
    fun get(numberPage: Int, proect: Proect): List<People>
}
class GetVotesByPage(
    private val storage: ProectsStorage
) : GetVotesOperation {
    override fun get(numberPage: Int, proect: Proect) = storage.getVotesByPageNum(numberPage,proect)
}