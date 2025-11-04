package ru.ezhov.duplicate.interfaces

import org.junit.jupiter.api.Test
import ru.ezhov.duplicate.interfaces.service.PaginationService

internal class PaginationServiceTest {
    @Test
    fun test() {
        var calculate = PaginationService(currentPage = 1, pageSize = 5, countItem = 2).calculate { "" }
        println(calculate)

        calculate = PaginationService(currentPage = 1, pageSize = 5, countItem = 10).calculate { "" }
        println(calculate)

        calculate = PaginationService(currentPage = 1, pageSize = 5, countItem = 2).calculate { "" }
        println(calculate)

        calculate = PaginationService(currentPage = 1, pageSize = 5, countItem = 15).calculate { "" }
        println(calculate)

        calculate = PaginationService(currentPage = 2, pageSize = 5, countItem = 15).calculate { "" }
        println(calculate)
    }
}
