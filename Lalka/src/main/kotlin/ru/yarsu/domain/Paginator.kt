package ru.yarsu.domain

class Paginator(private val url: String, private val numberPage: Int, private val countPage: Int) {

    fun previousPage(): Boolean{
        return numberPage != 1
    }
    fun nextPage(): Boolean{
        return numberPage != countPage
    }

    fun linkMaker(str: String?, n: Int): String? {
        return if (str == null || str.length < n) {
            str
        } else str.substring(0, str.length - n)
    }


    fun nextLink(): String {
        val a = Paginator(linkMaker(url, numberPage.toString().length) + (numberPage+1).toString(), numberPage+1 , countPage)
        return a.url
    }
    fun previousLink(): String {
        val a = Paginator(linkMaker(url, numberPage.toString().length) + (numberPage-1).toString(), numberPage-1 , countPage)
        return a.url
    }

    fun getNumberPage(): Int{
        return numberPage
    }
}