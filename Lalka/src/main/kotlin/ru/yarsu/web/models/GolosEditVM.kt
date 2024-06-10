package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.domain.Golos
import ru.yarsu.domain.Proect

class GolosEditVM(val golos: Golos, val proects: List<Proect>): ViewModel {
}