package ru.ezhov.duplicate.domain.duplicate

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.duplicate.model.Part
import ru.ezhov.duplicate.view.FileService

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
                                fileService.mimeType(file!!.absolutePath) ?: defaultMimeType,
                                file.inputStream()
                            )
                        }

                }
            }
            ?: PartFileInfo(
                defaultMimeType,
                this.javaClass.getResourceAsStream("/static/not-found.jpg")
            )

    fun by(partId: String): Part? = partRepository.findByIdOrNull(partId)

    companion object {
        private const val defaultMimeType = "image/jpeg"
    }
}
