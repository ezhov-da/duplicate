package ru.ezhov.duplicate.interfaces.service

import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files

const val DEFAULT_MIME_TYPE = "image/jpeg"

@Service
class FileService {
    fun mimeType(filePath: String): String? =
        File(filePath)
            .takeIf { it.exists() }
            ?.let {
                Files.probeContentType(it.toPath())
            }

    fun mimeTypeOrDefault(filePath: String): String =
        File(filePath)
            .takeIf { it.exists() }
            ?.let { Files.probeContentType(it.toPath()) }
            ?: DEFAULT_MIME_TYPE

    fun fileType(filePath: String): FileType =
        File(filePath)
            .takeIf { it.exists() }
            ?.let {
                val type = this.mimeType(filePath)?.substringBefore("/")
                FileType
                    .values()
                    .toList()
                    .firstOrNull { it.name == type?.uppercase() }
            }
            ?: FileType.OTHER
}
