package ru.ezhov.duplicate.view

class IndexPage(
        val username:String,
        val uploads: List<UploadView>,
        pagination: PaginationView? = null
) : GeneralPage(pagination)