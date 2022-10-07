package ru.ezhov.duplicate.domain.duplicate

import ru.ezhov.duplicate.domain.duplicate.model.Part

interface PartRepository {
    fun by(id: String): Part?

    fun save(parts: List<Part>)
}