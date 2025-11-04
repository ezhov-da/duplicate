package ru.ezhov.duplicate.domain.duplicate

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.duplicate.model.Part
import ru.ezhov.duplicate.interfaces.service.FileService

@Service
class PartService(
    private val partRepository: PartRepository,
    private val fileService: FileService
) {

    fun data(id: String): PartFileInfo =
        partRepository
            .findByIdOrNull(id)
            ?.let { part ->
                part.file.let { file ->
                    file
                        .takeIf { it.exists() }
                        ?.let {
                            PartFileInfo(
                                mimeType = fileService.mimeType(file.absolutePath) ?: DEFAULT_MIME_TYPE,
                                data = file.inputStream()
                            )
                        }

                }
            }
            ?: PartFileInfo(
                mimeType = DEFAULT_MIME_TYPE,
                data = this.javaClass.getResourceAsStream("/static/not-found.jpg")!!
            )

    fun by(partId: String): Part? = partRepository.findByIdOrNull(partId)

    companion object {
        private const val DEFAULT_MIME_TYPE = "image/jpeg"
    }
}
