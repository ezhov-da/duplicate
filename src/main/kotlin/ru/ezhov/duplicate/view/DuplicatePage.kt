package ru.ezhov.duplicate.view

class DuplicatePage(
        val username: String,
        pagination: PaginationView? = null,
        val selectedPage: String,
        val duplicates: DuplicatesView
) : GeneralPage(pagination)