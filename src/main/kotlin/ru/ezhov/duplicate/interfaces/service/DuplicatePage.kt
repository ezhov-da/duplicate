package ru.ezhov.duplicate.interfaces.service

import ru.ezhov.duplicate.interfaces.controller.model.DuplicatesView
import ru.ezhov.duplicate.interfaces.controller.model.GeneralPage
import ru.ezhov.duplicate.interfaces.controller.model.PaginationView
import ru.ezhov.duplicate.interfaces.filter.FilterView

class DuplicatePage(
    val username: String,
    val selectedPage: String,
    val filters: List<FilterView>,
    val duplicates: DuplicatesView,
    pagination: PaginationView? = null,
) : GeneralPage(pagination)
