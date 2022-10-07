package ru.ezhov.duplicate.infrastructure

import ru.ezhov.duplicate.domain.duplicate.DuplicateRepository
import ru.ezhov.duplicate.domain.duplicate.FilterService
import ru.ezhov.duplicate.domain.duplicate.PartRepository

class DefaultFilterService(
    private val duplicateRepository: DuplicateRepository,
    private val partRepository: PartRepository
) : FilterService {
    override fun availableFileTypes(uploadId: String): List<String> =
            duplicateRepository
                    .by(uploadId)
                    .map { d ->
                        d
                                .partIds
                                .mapNotNull { partId ->
                                    partRepository.by(partId)?.fileType
                                }
                    }
                    .flatten()
                    .distinct()
}