package ru.ezhov.duplicate.view

import ru.ezhov.duplicate.view.filter.FilterView

class SelectedPage(
        val username: String,
        val uploadId: String,
        val filters: List<FilterView>,
        val duplicatesLink: String,
        val parts: List<SelectedPartView>,
        pagination: PaginationView? = null
) : GeneralPage(pagination)