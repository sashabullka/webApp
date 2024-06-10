package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.domain.Proect

class GolosAddVM(val proects: List<Proect>): ViewModel {
}