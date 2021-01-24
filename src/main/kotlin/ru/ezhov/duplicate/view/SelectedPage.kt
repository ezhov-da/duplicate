package ru.ezhov.duplicate.view

import ru.ezhov.duplicate.view.filter.FilterView

class SelectedPage(
        val username: String,
        val uploadId: String,
        val filters: List<FilterView>,
        val duplicatesLink: String,
        val parts: List<SelectedPartView>,
        val removeScriptLink: String,
        pagination: PaginationView? = null
) : GeneralPage(pagination)