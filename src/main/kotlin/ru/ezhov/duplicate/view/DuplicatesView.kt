package ru.ezhov.duplicate.view

data class DuplicatesView(
        val uploadId: String,
        val duplicates: List<DuplicateView>
)