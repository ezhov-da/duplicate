package ru.ezhov.duplicate.infrastructure

import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.DuplicateRepository
import ru.ezhov.duplicate.domain.FilterService
import ru.ezhov.duplicate.domain.PartRepository
import java.util.concurrent.ConcurrentHashMap

@Service
class CacheFilterService(
        duplicateRepository: DuplicateRepository,
        partRepository: PartRepository
) : FilterService {
    private val defaultFilterService = DefaultFilterService(duplicateRepository, partRepository)

    private val fileTypes = ConcurrentHashMap<String, List<String>>()

    override fun availableFileTypes(uploadId: String): List<String> {
        if (!fileTypes.containsKey(uploadId)) {
            fileTypes[uploadId] = defaultFilterService.availableFileTypes(uploadId)
        }
        return fileTypes[uploadId] ?: emptyList()
    }
}