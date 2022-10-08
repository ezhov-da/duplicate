package ru.ezhov.duplicate.infrastructure

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.duplicate.FilterService
import ru.ezhov.duplicate.domain.duplicate.UploadRepository

@Service
class DefaultFilterService(
    private val uploadRepository: UploadRepository,
) : FilterService {
    override fun availableFileTypes(uploadId: String): List<String> =
        uploadRepository
            .findByIdOrNull(uploadId)
            ?.duplicates
            ?.map { duplicate ->
                duplicate
                    .parts
                    .map { part -> part.fileType() }
            }
            .orEmpty()
            .flatten()
            .distinct()
}
