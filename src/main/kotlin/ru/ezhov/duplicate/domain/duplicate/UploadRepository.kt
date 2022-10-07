package ru.ezhov.duplicate.domain.duplicate

import ru.ezhov.duplicate.domain.duplicate.model.Upload

interface UploadRepository {
    fun all(): List<Upload>

    fun save(upload: List<Upload>)

    fun by(id: String): Upload?
}