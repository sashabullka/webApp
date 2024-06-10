package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.domain.Proect

class ErrorProectFormVM(val listErrors: List<String>, val nameP: String?, val nameT: String?, val coordinates: String?, val description: String?, val proects: List<Proect>): ViewModel {
}