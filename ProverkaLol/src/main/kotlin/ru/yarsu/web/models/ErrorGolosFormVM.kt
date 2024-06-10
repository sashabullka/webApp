package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.domain.Proect

class ErrorGolosFormVM(val listErrors: List<String>, val name: String?, val proects: List<Proect>): ViewModel {
}