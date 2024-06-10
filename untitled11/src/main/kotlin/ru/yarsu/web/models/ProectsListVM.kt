package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.domain.Paginator
import ru.yarsu.domain.People
import ru.yarsu.domain.Proect

class ProectsListVM(val proect: Proect, val listVote: List<People>, val page: Paginator, val pageNum: Int,val proects: List<Proect>, val min: Double?, val max: Double?, val empty: Boolean) : ViewModel {
}