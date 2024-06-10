package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.domain.Golos
import ru.yarsu.domain.Proect

class GolosVM(val golos: Golos, val proects: List<Proect>, val page: Int, val proect: Proect): ViewModel {
}