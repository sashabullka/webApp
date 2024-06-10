package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.domain.Proect

class ProectDeleteVM(val proect: Proect, val proects: List<Proect>): ViewModel {
}