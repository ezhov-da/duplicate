package ru.ezhov.duplicate.interfaces.controller.model

data class PartView(
    val id: String,
    val path: String,
    val name: String,
    val previewLink: String,
    val link: String,
    val selected: Boolean = false,
    val selectLink: String,
    val fileType: String
)
