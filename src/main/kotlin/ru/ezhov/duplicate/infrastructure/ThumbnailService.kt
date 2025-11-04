package ru.ezhov.duplicate.infrastructure

import net.coobird.thumbnailator.Thumbnails
import org.springframework.stereotype.Service
import java.io.File

// TODO ezhov завершить
@Service
class ThumbnailService {
    fun createThumbnail(id: String, path: String) {
        val thumbnailFolder = File(System.getProperty("user.home"), ".duplicate-thumbnails").apply { mkdirs() }
        val originFile = File(path)
        val extension = path.substringAfterLast(".")

        Thumbnails.of(originFile)
            .size(100, 100)
            .outputFormat(extension)
            .toFile(File(thumbnailFolder, "${id}${extension.takeIf { it.isNotEmpty() }?.let { ".$it" }}"))
    }
}
