package ru.ezhov.duplicate.view

data class PaginationView(
        val currentPageSelect: Int,
        val previous: Int?,
        val previousLink: String?,
        val firstPage: Int?,
        val firstPageLink: String?,
        val firstDots: Boolean,
        val preCurrent: Int?,
        val preCurrentLink: String?,
        val current: Int?,
        val currentLink: String?,
        val postCurrent: Int?,
        val postCurrentLink: String?,
        val lastDots: Boolean,
        val lastPage: Int?,
        val lastPageLink: String?,
        val next: Int?,
        val nextLink: String?,
)