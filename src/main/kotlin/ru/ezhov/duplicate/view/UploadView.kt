package ru.ezhov.duplicate.view

data class UploadView(
        val id: String,
        val name: String,
        val date: String,
        val duplicatesLink: String,
        val selectedLink: String?,
        val selectedCount: Int,
        val duplicatesCount: Int
)