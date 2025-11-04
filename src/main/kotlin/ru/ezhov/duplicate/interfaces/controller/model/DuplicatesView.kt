package ru.ezhov.duplicate.interfaces.controller.model

data class DuplicatesView(
    val uploadId: String,
    val duplicates: List<DuplicateView>
)
