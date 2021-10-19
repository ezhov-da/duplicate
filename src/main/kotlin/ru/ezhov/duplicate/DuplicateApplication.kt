package ru.ezhov.duplicate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DuplicateApplication

fun main(args: Array<String>) {
    runApplication<DuplicateApplication>(*args)
}