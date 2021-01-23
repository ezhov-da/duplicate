package ru.ezhov.duplicate.domain

import org.springframework.stereotype.Service

@Service
class DuplicateSelectedService(
        private val partRepository: PartRepository,
        private val partSelectedRepository: PartSelectedRepository
) {
    fun selected(uploadId: String): List<Part> {
        val selectIds = partSelectedRepository.selectIds(uploadId)
        return selectIds
                .mapNotNull { partRepository.by(it) }
    }
}