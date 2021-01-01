package ru.ezhov.duplicate.view

import ru.ezhov.duplicate.view.filter.FilterView

class DuplicatePage(
        val username: String,
        val selectedPage: String,
        val filters: List<FilterView>,
        val duplicates: DuplicatesView,
        pagination: PaginationView? = null,
) : GeneralPage(pagination)