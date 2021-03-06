package ru.ezhov.duplicate.domain

import java.time.LocalDateTime
import java.util.*

class Upload private constructor(
        val id: String,
        val name: String,
        val date: LocalDateTime,
) {

    companion object {
        fun generateId() = UUID.randomUUID().toString()

        fun create(
                id: String,
                name: String
        ) = Upload(
                id = id,
                name = name,
                date = LocalDateTime.now()
        )

        fun from(
                id: String,
                name: String,
                date: LocalDateTime
        ) = Upload(
                id = id,
                name = name,
                date = date
        )
    }
}