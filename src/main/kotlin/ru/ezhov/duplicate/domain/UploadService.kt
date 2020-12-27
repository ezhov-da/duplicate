package ru.ezhov.duplicate.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UploadService(
        @Autowired private val uploadRepository: UploadRepository
) {
    fun all(): List<Upload> = uploadRepository.all()

    fun save(upload: List<Upload>) {
        uploadRepository.save(upload)
    }

    fun by(id: String): Upload? = uploadRepository.by(id)
}