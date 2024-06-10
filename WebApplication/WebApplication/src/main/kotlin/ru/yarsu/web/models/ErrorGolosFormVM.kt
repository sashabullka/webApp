package ru.yarsu.web.models

import org.http4k.template.ViewModel


class ErrorGolosFormVM(val listErrors: List<String>, val name: String?): ViewModel {
}