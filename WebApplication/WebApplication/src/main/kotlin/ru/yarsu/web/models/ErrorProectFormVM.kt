package ru.yarsu.web.models

import org.http4k.template.ViewModel


class ErrorProectFormVM(val listErrors: List<String>, val nameP: String?, val nameT: String?, val coordinates: String?, val description: String?, ): ViewModel {
}