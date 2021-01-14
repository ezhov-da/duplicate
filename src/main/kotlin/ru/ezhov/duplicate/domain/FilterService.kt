package ru.ezhov.duplicate.domain

interface FilterService {
    fun availableFileTypes(uploadId: String): List<String>
}