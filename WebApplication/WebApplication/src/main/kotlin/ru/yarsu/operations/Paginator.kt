package ru.yarsu.operations

import org.http4k.core.Uri

class Paginator(private val url: Uri, private val numberPage: Int, private val countPage: Int) {

    fun previousPage(): Boolean{
        return numberPage != 1
    }
    fun nextPage(): Boolean{
        return numberPage != countPage
    }

    fun nextLink(): Uri {
        return createLink(numberPage + 1)
    }

    fun previousLink(): Uri {
        return createLink(numberPage - 1)
    }

    private fun createLink(newPageNumber: Int): Uri {
        val pathSegments = url.path.split("/").toMutableList()
        if (pathSegments.isNotEmpty()) {
            pathSegments[pathSegments.size - 1] = newPageNumber.toString()
        }
        val newPath = pathSegments.joinToString("/")

        return url.path(newPath)
    }

    fun getNumberPage(): Int{
        return numberPage
    }
}