package ru.ezhov.duplicate.domain

interface PartRepository {
    fun by(id: String): Part?

    fun save(parts: List<Part>)
}