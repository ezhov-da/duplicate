package ru.ezhov.duplicate.view

class SelectedPage(
        val uploadId: String,
        val duplicatesLink: String,
        val parts: List<SelectedPartView>,
        pagination: PaginationView? = null,
) : GeneralPage(pagination)