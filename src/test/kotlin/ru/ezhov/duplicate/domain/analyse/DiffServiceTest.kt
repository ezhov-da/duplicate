package ru.ezhov.duplicate.domain.analyse

import org.junit.jupiter.api.Test

class DiffServiceTest {
    @Test
    fun test() {

        val paths = listOf(
            "C:/Users/DEzhov/repository/duplicate/example/file_example_JPG_100kB — копия — копия.jpg",
            "C:/Users/DEzhov/repository/duplicate/example/file_example_JPG_100kB — копия.jpg",
            "C:/Users/DEzhov/repository/duplicate/example/file_example_JPG_100kB.jpg",
            "C:/Users/DEzhov/repository/duplicate/example/sub/file_example_JPG_100kB — копия.jpg",
        )
        val service = DiffService().diff(paths)
    }
}
