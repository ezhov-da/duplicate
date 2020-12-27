package ru.ezhov.duplicate.domain

interface PartSelectedRepository {
    fun select(uploadId: String, idPart: String): Boolean

    fun selectIds(uploadId: String): List<String>
}