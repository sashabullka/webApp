package ru.yarsu.domain

data class Permisson (
    val canAddProect: Boolean,
    val canAddGolos: Boolean,
    val canEditProect: Boolean,
    val canEditGolos: Boolean,
    val canDeleteProect: Boolean,
    val canDeleteGolos: Boolean,
    val canListUsers: Boolean,
    val canWatchWhatHeGolosFor: Boolean,
    )
