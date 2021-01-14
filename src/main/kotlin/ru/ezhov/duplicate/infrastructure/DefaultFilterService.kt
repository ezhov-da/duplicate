package ru.ezhov.duplicate.infrastructure

import ru.ezhov.duplicate.domain.DuplicateRepository
import ru.ezhov.duplicate.domain.FilterService
import ru.ezhov.duplicate.domain.PartRepository

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