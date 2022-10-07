package ru.ezhov.duplicate.domain.duplicate

interface FilterService {
    fun availableFileTypes(uploadId: String): List<String>
}