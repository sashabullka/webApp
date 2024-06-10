package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.domain.Golos
import ru.yarsu.domain.Proect

class UserGolosaVM( val list: List<Golos>?,val proect: Proect, val page: Int): ViewModel {
}