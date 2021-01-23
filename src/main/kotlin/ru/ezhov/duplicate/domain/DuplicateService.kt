package ru.ezhov.duplicate.domain

import org.springframework.stereotype.Service

@Service
class DuplicateService(
        private val duplicateRepository: DuplicateRepository
) {
    fun all() = duplicateRepository.all()

    fun by(uploadId: String) = duplicateRepository.by(uploadId)
}