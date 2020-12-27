package ru.ezhov.duplicate.domain

data class Duplicate(
        val id: String,
        val uploadId: String,
        val partIds: List<String>
)