package ru.ezhov.duplicate.domain.duplicate

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.duplicate.model.Part

@Service
class DuplicateSelectedService(
    private val uploadRepository: UploadRepository,
) {
    fun selected(uploadId: String): List<Part> =
        uploadRepository.findByIdOrNull(uploadId)?.let { upload ->
            upload.duplicates.map { duplicate ->
                duplicate.parts.filter { part -> part.selected }
            }
        }.orEmpty()
            .flatten()
}
