package ru.ezhov.duplicate.view

data class PartView(
        val id: String,
        val path: String,
        val name: String,
        val link: String,
        val selected: Boolean = false,
        val selectLink: String,
        val fileType: String
)