package ru.ezhov.duplicate.domain

import org.springframework.stereotype.Service

@Service
class FilterService(
        private val duplicateRepository: DuplicateRepository,
        private val partRepository: PartRepository
) {
    fun availableFileTypes(uploadId: String): List<String> =
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