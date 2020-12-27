package ru.ezhov.duplicate.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DuplicateSelectedService(
        @Autowired private val partRepository: PartRepository,
        @Autowired private val partSelectedRepository: PartSelectedRepository
) {
    fun selected(uploadId: String): List<Part> {
        val selectIds = partSelectedRepository.selectIds(uploadId)
        return selectIds
                .mapNotNull { partRepository.by(it) }
    }
}