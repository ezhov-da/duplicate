package ru.ezhov.duplicate.view

data class DuplicateView(
        val id: String,
        val commonNamePart: String?,
        val parts: List<PartView>
)