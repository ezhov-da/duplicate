package ru.ezhov.duplicate.domain.duplicate

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.duplicate.model.Duplicate

@Service
class DuplicateService(
    private val uploadRepository: UploadRepository
) {
    fun by(uploadId: String): List<Duplicate> = uploadRepository
        .findByIdOrNull(uploadId)
        ?.duplicates
        .orEmpty()
}
