package ru.ezhov.duplicate.view

import org.junit.jupiter.api.Test

internal class CommonPartServiceTest {
    @Test
    fun test() {
        val commonNameService = CommonPartService()

        var value = commonNameService.commonPart(listOf(
                "1234egazdbzxfbfzdf",
                "1234safvasawveaev",
                "1234asvsbasrbasbarsb",
                "1234ewrsbsebsdhsdfgadfg",
        ))

        println(value)

        value = commonNameService.commonPart(listOf(
                "1e",
                "1g"
        ))

        println(value)

        value = commonNameService.commonPart(listOf(
                "11",
                "22"
        ))

        println(value)
    }
}