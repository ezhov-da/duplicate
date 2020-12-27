package ru.ezhov.duplicate.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DuplicateService(
        @Autowired private val duplicateRepository: DuplicateRepository
) {
    fun all() = duplicateRepository.all()

    fun by(uploadId: String) = duplicateRepository.by(uploadId)
}