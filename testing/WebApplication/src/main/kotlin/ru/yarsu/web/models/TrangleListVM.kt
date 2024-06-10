package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.domain.Triangle

class TrangleListVM(val triangles: List<Triangle>) : ViewModel