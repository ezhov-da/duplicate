package ru.ezhov.duplicate.domain

interface PartRepository {
    fun all(): List<Part>

    fun by(id: String): Part?

    fun save(parts: List<Part>)
}