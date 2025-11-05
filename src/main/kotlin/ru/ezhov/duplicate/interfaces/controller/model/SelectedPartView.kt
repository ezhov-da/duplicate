package ru.ezhov.duplicate.interfaces.controller.model

data class SelectedPartView(
    val id: String,
    val path: String,
    val name: String,
    val previewLink: String,
    val link: String,
    val fileType: String,
)
