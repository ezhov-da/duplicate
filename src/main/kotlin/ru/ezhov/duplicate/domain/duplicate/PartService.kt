package ru.ezhov.duplicate.domain.duplicate

import org.springframework.stereotype.Service
import ru.ezhov.duplicate.domain.duplicate.model.Part
import ru.ezhov.duplicate.view.FileService

@Service
class PartService(
    private val partRepository: PartRepository,
    private val fileService: FileService
) {

    fun data(id: String): PartFileInfo {
        val part = partRepository.by(id)
        val file = part?.file
        return file
                .takeIf { it?.exists() ?: false }
                ?.let {
                    PartFileInfo(
                            fileService.mimeType(file!!.absolutePath) ?: defaultMimeType,
                            file.inputStream()
                    )
                }
                ?: PartFileInfo(
                        defaultMimeType,
                        this.javaClass.getResourceAsStream("/static/not-found.jpg")
                )
    }

    fun by(partId: String): Part? = partRepository.by(partId)

    companion object {
        private const val defaultMimeType = "image/jpeg"
    }
}