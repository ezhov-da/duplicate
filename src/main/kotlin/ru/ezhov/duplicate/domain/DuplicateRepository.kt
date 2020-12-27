package ru.ezhov.duplicate.domain

interface DuplicateRepository {
    fun all(): List<Duplicate>

    fun by(uploadId: String): List<Duplicate>

    fun save(duplicates: List<Duplicate>)
}