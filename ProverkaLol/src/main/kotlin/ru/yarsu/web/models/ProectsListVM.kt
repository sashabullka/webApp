package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.domain.Golos
import ru.yarsu.domain.Paginator
import ru.yarsu.domain.People
import ru.yarsu.domain.Proect

class ProectsListVM(val proect: Proect, val listVote: List<Golos>, val page: Paginator, val pageNum: Int, val proects: List<Proect>, val min: Int?, val max: Int?, val empty: Boolean) : ViewModel {
}