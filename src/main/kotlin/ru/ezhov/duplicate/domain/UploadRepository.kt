package ru.ezhov.duplicate.domain

interface UploadRepository {
    fun all(): List<Upload>

    fun save(upload: List<Upload>)

    fun by(id: String): Upload?
}