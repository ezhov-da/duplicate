package ru.ezhov.duplicate.view

class IndexPage(
        val uploads: List<UploadView>,
        pagination: PaginationView? = null
) : GeneralPage(pagination)