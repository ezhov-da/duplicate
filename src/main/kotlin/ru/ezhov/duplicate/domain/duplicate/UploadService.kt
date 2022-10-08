package ru.ezhov.duplicate.domain.duplicate

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.duplicate.model.Upload

@Service
class UploadService(
    private val uploadRepository: UploadRepository,
    private val partRepository: PartRepository,
) {
    fun all(): List<Upload> = uploadRepository.findAll()

    fun save(upload: Upload) {
        uploadRepository.save(upload)
    }

    fun by(id: String): Upload? = uploadRepository.findById(id).orElse(null)

    fun select(uploadId: String, partId: String): Boolean =
        partRepository.findByIdOrNull(partId)?.let { part ->
            val new = !part.selected
            part.selected = new
            partRepository.save(part)
            new
        }
            ?: false
}
