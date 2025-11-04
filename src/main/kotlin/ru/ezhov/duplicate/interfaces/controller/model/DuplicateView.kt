package ru.ezhov.duplicate.interfaces.controller.model

data class DuplicateView(
    val id: String,
    val commonNamePart: String?,
    val parts: List<PartView>
)
