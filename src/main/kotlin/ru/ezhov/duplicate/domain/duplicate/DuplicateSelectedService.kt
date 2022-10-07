package ru.ezhov.duplicate.domain.duplicate

import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.duplicate.model.Part

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