package ru.ezhov.duplicate.domain

import org.springframework.stereotype.Service
import ru.ezhov.duplicate.view.FileService

@Service
class PartService(
        private val partRepository: PartRepository,
        private val fileService: FileService

) {

    fun all() = partRepository.all()

    fun data(id: String): PartFileView {
        val part = partRepository.by(id)
        val file = part?.file
        return file
                .takeIf { it?.exists() ?: false }
                ?.let {
                    PartFileView(
                            fileService.mimeType(file!!.absolutePath) ?: defaultMimeType,
                            file.readBytes()
                    )
                }
                ?: PartFileView(
                        defaultMimeType,
                        this.javaClass.getResource("/static/not-found.jpg").readBytes()
                )
    }

    fun by(partId: String): Part? = partRepository.by(partId)

    companion object {
        private const val defaultMimeType = "image/jpeg"
    }
}