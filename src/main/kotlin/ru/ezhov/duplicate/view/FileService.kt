package ru.ezhov.duplicate.view

import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@Service
class FileService {
    fun mimeType(filePath: String): String {
        val path: Path = File(filePath).toPath()
        return Files.probeContentType(path)
    }

    fun fileType(filePath: String): FileType =
            File(filePath)
                    .takeIf { it.exists() }
                    .let {
                        val type = this.mimeType(filePath).substringBefore("/")
                        FileType.values().toList().firstOrNull { it.name == type.toUpperCase() }
                    }
                    ?: FileType.OTHER
}