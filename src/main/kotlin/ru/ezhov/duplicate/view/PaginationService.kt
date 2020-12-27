package ru.ezhov.duplicate.view

import kotlin.math.ceil

class PaginationService(
        private val currentPage: Int,
        pageSize: Int,
        countItem: Int,
) {
    private val pagesCount = (if ((countItem / pageSize) == 0) (countItem / pageSize) + 1 else ceil((countItem.toDouble() / pageSize))).toInt()

    fun calculate(linkPageBuilder: (page: Int?) -> String?): PaginationView = PaginationView(
            currentPageSelect = currentPage,
            previous = previous(),
            previousLink = linkPageBuilder(previous()),
            firstPage = firstPage(),
            firstPageLink = linkPageBuilder(firstPage()),
            firstDots = firstDots(),
            preCurrent = preCurrent(),
            preCurrentLink = linkPageBuilder(preCurrent()),
            current = current(),
            currentLink = linkPageBuilder(current()),
            postCurrent = postCurrent(),
            postCurrentLink = linkPageBuilder(postCurrent()),
            lastDots = lastDots(),
            lastPage = lastPage(),
            lastPageLink = linkPageBuilder(lastPage()),
            next = next(),
            nextLink = linkPageBuilder(next())
    )

    private fun previous() = if (currentPage - 1 == 0) null else currentPage - 1
    private fun firstPage() = if (currentPage == 1 || pagesCount == 1 || pagesCount > 1) 1 else null
    private fun firstDots() = (currentPage - 2 != 1 && currentPage - 2 > 0)
    private fun preCurrent() = if (currentPage - 1 > 1) currentPage - 1 else null
    private fun current() = if (pagesCount > 1 && currentPage != 1) currentPage else null
    private fun postCurrent() = if (currentPage + 1 < pagesCount) currentPage + 1 else null
    private fun lastDots() = (currentPage + 2 < pagesCount)
    private fun lastPage() = if (currentPage != pagesCount && pagesCount > 1) pagesCount else null
    private fun next() = if (currentPage + 1 <= pagesCount) currentPage + 1 else null
}