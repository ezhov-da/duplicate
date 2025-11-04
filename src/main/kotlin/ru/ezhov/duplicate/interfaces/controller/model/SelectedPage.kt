package ru.ezhov.duplicate.interfaces.controller.model

import ru.ezhov.duplicate.interfaces.filter.FilterView

class SelectedPage(
    val username: String,
    val uploadId: String,
    val filters: List<FilterView>,
    val duplicatesLink: String,
    val parts: List<SelectedPartView>,
    val removeScriptLink: String,
    pagination: PaginationView? = null
) : GeneralPage(pagination)
