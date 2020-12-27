package ru.ezhov.duplicate.view

class DuplicatePage(
        pagination: PaginationView? = null,
        val selectedPage: String,
        val duplicates: DuplicatesView
) : GeneralPage(pagination)