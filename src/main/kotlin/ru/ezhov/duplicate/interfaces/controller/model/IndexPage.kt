package ru.ezhov.duplicate.interfaces.controller.model

class IndexPage(
    val username: String,
    val uploads: List<UploadView>,
    pagination: PaginationView? = null
) : GeneralPage(pagination)
